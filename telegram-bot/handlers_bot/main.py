
from aiogram.exceptions import TelegramBadRequest
from aiogram import Bot, types, Router
from aiogram.types import Message, ReplyKeyboardRemove, CallbackQuery
from aiogram.filters import Command, StateFilter
from keyboards_bot import main_menu


router = Router()

@router.message(Command('start'))
async def start(message: Message):
    text = 'Привет! Этот бот умеет подбирать рацион питания. Для составления рациона заполните нужные поля. После этого будет доступна функция фильтрация предложенных рационов питания.'
    await message.answer(text, reply_markup=main_menu.InlineKeyboard().start)


@router.message(Command("clear"))
async def all_clear(message: Message, bot: Bot):
    try:
        for i in range(message.message_id, 0, -1):
            await bot.delete_message(message.from_user.id, i)
    except TelegramBadRequest as ex:
        if ex.message == "Bad Request: message to delete not found":
            raise Exception("Message not found")