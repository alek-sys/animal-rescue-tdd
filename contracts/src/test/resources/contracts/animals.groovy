import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/animals'
        headers {
            contentType('application/json')
        }
    }
    response {
        status OK()
        body([
            [
              "id": $(regex("\\d+")),
              "name": anyNonBlankString(),
              "description": regex(".?"),
              "avatarUrl": anyHttpsUrl(),
              "rescueDate": regex(".+")
            ]
        ])
        headers {
            contentType('application/json')
        }
    }
}