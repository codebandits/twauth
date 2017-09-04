import nightmare from 'nightmare'

const CHAT_URL = process.env.CHAT_URL || 'http://localhost:3000'

describe('twauth chat', function () {

    test('should prompt the user to login', async function () {
        let page = nightmare().goto(CHAT_URL)

        let text = await page.evaluate(() => document.body.innerText)
            .end()

        expect(text).toContain("Login")
    })
})
