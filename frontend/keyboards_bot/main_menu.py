from aiogram import Bot, types
from aiogram.types import BotCommand
from aiogram.types.web_app_info import WebAppInfo
from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton

async def default_commands(bot: Bot):
    menu_commands = [
        BotCommand(command="/start", description="Start bot"),
        BotCommand(command="/clear", description="Clear all messages"),
    ]

    await bot.set_my_commands(menu_commands)


class InlineKeyboard:
    @property
    def start(self):

        inline_keyboard = [
            [types.InlineKeyboardButton(text='Открыть приложение', web_app=WebAppInfo(url="https://158.160.2.48/start"))]
        ]
        return types.InlineKeyboardMarkup(inline_keyboard=inline_keyboard)