import asyncio
import logging
from aiogram import Bot, Dispatcher, types
from private import TOKEN
from handlers_bot import main
from keyboards_bot.main_menu import default_commands


logging.basicConfig(level=logging.INFO)
bot = Bot(token=TOKEN)

dp = Dispatcher()
dp.include_router(main.router)


dp.startup.register(default_commands)
dp.run_polling(bot)

async def main():
    await dp.start_polling(bot)

if __name__ == '__main__':
    asyncio.run(main())