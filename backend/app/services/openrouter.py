"""Small, optional OpenRouter chat-completion adapter using the Python standard library."""

import json
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen

from app.core.config import Settings


OPENROUTER_CHAT_COMPLETIONS_URL = "https://openrouter.ai/api/v1/chat/completions"


class OpenRouterError(RuntimeError):
    """Safe-to-display provider failure without response bodies or credentials."""


def generate_reply(message: str, settings: Settings) -> str:
    """Send one user message to the configured OpenRouter model and return its text."""
    api_key = settings.openrouter_api_key
    if api_key is None or not api_key.get_secret_value().strip():
        raise OpenRouterError("OpenRouter is not configured.")

    payload = {
        "model": settings.openrouter_model,
        "messages": [
            {
                "role": "system",
                "content": (
                    "You are MTech Personal AI, a helpful assistant in a student research "
                    "prototype. Answer clearly and concisely. You cannot access the user's "
                    "phone, files, schedule, contacts, or IoT devices unless their content "
                    "is provided in this conversation. Never claim to have performed an "
                    "action outside this chat."
                ),
            },
            {"role": "user", "content": message},
        ],
        "max_tokens": 500,
        "temperature": 0.2,
    }
    request = Request(
        OPENROUTER_CHAT_COMPLETIONS_URL,
        data=json.dumps(payload).encode("utf-8"),
        headers={
            "Authorization": f"Bearer {api_key.get_secret_value()}",
            "Content-Type": "application/json",
        },
        method="POST",
    )

    try:
        with urlopen(request, timeout=settings.openrouter_timeout_seconds) as response:
            result = json.loads(response.read().decode("utf-8"))
    except HTTPError as error:
        raise OpenRouterError(f"OpenRouter returned HTTP {error.code}.") from None
    except (URLError, TimeoutError, OSError):
        raise OpenRouterError("Could not reach OpenRouter. Check the connection and try again.") from None
    except (UnicodeDecodeError, json.JSONDecodeError):
        raise OpenRouterError("OpenRouter returned an unreadable response.") from None

    try:
        reply = result["choices"][0]["message"]["content"]
    except (KeyError, IndexError, TypeError):
        raise OpenRouterError("OpenRouter returned an unexpected response format.") from None

    if not isinstance(reply, str) or not reply.strip():
        raise OpenRouterError("OpenRouter returned an empty reply.")
    return reply.strip()
