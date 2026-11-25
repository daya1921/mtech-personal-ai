from functools import lru_cache

from pydantic import Field, SecretStr
from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    app_name: str = "MTech Personal AI"
    app_version: str = "0.1.0"
    environment: str = "development"
    openrouter_api_key: SecretStr | None = None
    openrouter_model: str = "openrouter/free"
    openrouter_timeout_seconds: float = Field(default=35.0, gt=0, le=120)

    model_config = SettingsConfigDict(
        env_prefix="MTECH_",
        env_file=".env",
        env_file_encoding="utf-8",
        extra="ignore",
    )


@lru_cache
def get_settings() -> Settings:
    return Settings()
