from typing import Literal

from fastapi import APIRouter
from pydantic import BaseModel, Field, field_validator


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
    mode: Literal["placeholder"] = "placeholder"
    reply: str


router = APIRouter(prefix="/api/v1/assistant", tags=["assistant"])


@router.post("/message", response_model=AssistantMessageResponse)
def receive_message(request: AssistantMessageRequest) -> AssistantMessageResponse:
    """Confirm the client-to-backend text path before model integration."""
    return AssistantMessageResponse(
        reply=(
            "Your message reached the MTech backend. AI responses will be added "
            "when a model provider is connected."
        )
    )
