from typing import Literal

from fastapi import APIRouter, HTTPException
from pydantic import BaseModel, Field, field_validator

from app.core.config import get_settings
from app.services.openrouter import OpenRouterError, generate_reply


class AssistantMessageRequest(BaseModel):
    message: str = Field(min_length=1, max_length=4_000)

    @field_validator("message")
    @classmethod
    def message_must_not_be_blank(cls, value: str) -> str:
        message = value.strip()
        if not message:
            raise ValueError("message must not be blank")
        return message


class AssistantMessageResponse(BaseModel):
    mode: Literal["placeholder", "openrouter"]
    reply: str


router = APIRouter(prefix="/api/v1/assistant", tags=["assistant"])


@router.post("/message", response_model=AssistantMessageResponse)
def receive_message(request: AssistantMessageRequest) -> AssistantMessageResponse:
    """Use OpenRouter when configured; otherwise keep the explicit placeholder."""
    settings = get_settings()
    api_key = settings.openrouter_api_key

    if api_key is not None and api_key.get_secret_value().strip():
        try:
            reply = generate_reply(request.message, settings)
        except OpenRouterError as error:
            raise HTTPException(status_code=502, detail=str(error)) from error
        return AssistantMessageResponse(mode="openrouter", reply=reply)

    return AssistantMessageResponse(
        mode="placeholder",
        reply=(
            "Your message reached the MTech backend. AI responses will be added "
            "when an OpenRouter API key is configured."
        )
    )
