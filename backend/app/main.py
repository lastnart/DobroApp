from contextlib import asynccontextmanager

from fastapi import FastAPI

from app.db import DATABASE_URL, SessionLocal, engine
from app.api.routes import router
from app.models import Base, Reward


@asynccontextmanager
async def lifespan(app: FastAPI):
    if DATABASE_URL.startswith("sqlite"):
        Base.metadata.drop_all(bind=engine)
    Base.metadata.create_all(bind=engine)
    db = SessionLocal()
    try:
        if db.query(Reward).count() == 0:
            db.add_all(
                [
                    Reward(title="Скидка 10% в кофейне Добро", category="Партнеры", cost=200),
                    Reward(title="Мини-курс Python", category="Курсы", cost=100),
                    Reward(title="Посадка дерева", category="Благотворительность", cost=150),
                ]
            )
        db.commit()
    finally:
        db.close()
    yield


app = FastAPI(title="DobroRyadom API", version="0.1.0", lifespan=lifespan)
app.include_router(router, prefix="/api")


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok"}