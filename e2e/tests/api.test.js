import nightmare from 'nightmare'

const API_URL = process.env.API_URL || 'http://localhost:8080'

describe('twauth api', function () {

    test('should be running', async function () {
        let page = nightmare().goto(API_URL + '/application/health')

        let text = await page.evaluate(() => document.body.innerText)
            .end()

        expect(text).toContain("UP")
    })
})
