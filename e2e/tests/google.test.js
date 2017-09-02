import nightmare from 'nightmare'

describe('The Google homepage', function () {

    test('it nurtures optimism', async function () {
        let page = nightmare().goto('http://www.google.com/')

        let text = await page.evaluate(() => document.body.textContent)
            .end()

        expect(text).toContain("Feeling Lucky")
    })
})
