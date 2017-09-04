import nightmare from 'nightmare'

describe('The Concourse homepage', function () {

    test('should describe Concourse', async function () {
        let page = nightmare().goto('http://concourse.ci/')

        let text = await page.evaluate(() => document.body.innerText)
            .end()

        expect(text).toContain("pipelines are defined as a single declarative config file")
    })
})
