# Animal Rescue ♥️😺 ♥️🐶 ♥️🐰 ♥️🐦 ♥️🐹
![Test All](https://github.com/alek-sys/animal-rescue/workflows/Test%20All/badge.svg?branch=main)

Sample Spring Boot app with React front-end.   

# Development

## Run locally 
Use the following commands to manage the local lifecycle of animal-rescue
```bash
./scripts/local.sh start         # start auth server, frontend app, and backend app
./scripts/local.sh start --quiet # start everything without launching the app in browser, and redirects all output to `./scripts/out/`
./scripts/local.sh stop          # stop auth server, frontend app, and backend app. You would only need to do this if you start the app in quiet mode.
```

## Try it out
Visit `https://localhost:3000/rescue`, you should see cute animal bios with the `Adopt` buttons disabled. All the information are fetched from a public `GET` backend endpoint `/animals`. 
![homepage](./docs/images/homepage.png)

Click the `Sign in to adopt` button on the top right corner, you should be redirected to the login page if you haven't already logged in. Use `alice / test` or `bob / test` credentials to log in. 

Once you logged in, you should see a greeting message regarding the username you log in with on the top right corner, and the `Adopt` buttons should be enabled.
![logged in view](./docs/images/logged-in.png)

Click on the `Adopt` button, input your contact email and application notes in the model, then click `Apply`, a `POST` request should be sent to a `sso-enabled` backend endpoint `/animals/{id}/adoption-requests`, with the adopter set to your username we parsed from your token.
![adopt model](./docs/images/adopt.png)   

Then the model should close, and you should see the `Adopt` button you clicked just now has turned into `Edit Adoption Request`. This is matched by your SSO log in username.
![adopted view](./docs/images/adopted.png)   

Click on the `Edit Adoption Request` again, you can view, edit (`PUT`), and delete (`DELETE`) the existing request.
![view or edit existing adoption request model](./docs/images/edit-or-delete.png)   

**Note**
Documentation may get out of date. Please refer to the [e2e test](./e2e/cypress/integration/) and the test output video for the most accurate user flow description.

## Local security configuration
Backend uses Form login for local development with two test accounts - `alice / test` and `bob / test`. 

> It is also possible to use GitHub for OAuth2 login for the app. This requires setting properties `github.client-id` and `github.client-secret` either via properties file or the environment.

## Tests
Execute the following script to run all tests:
```bash
./scripts/local.sh init          # install dependencies for the frontend folder and the e2e folder
./scripts/local.sh ci            # run backend tests and e2e tests
./scripts/local.sh backend       # run backend test only
./scripts/local.sh e2e --quiet   # run e2e test only without interactive mode
```

You can find an e2e test output video showing the whole journey in `./e2e/cypress/videos/` after the test run. 
If you would like to launch the test in an actual browser and run e2e test interactively, you may run the following commands:
```bash
./scripts/local.sh start
./scripts/local.sh e2e
``` 
More detail about the e2e testing framework can be found at [cypress api doc](https://docs.cypress.io/api/api/table-of-contents.html) 

# CI

## GitHub Actions
GitHub Actions run all checks for the `master` branch and all PR requests. All workflow configuration can be found in `.github/workflows`.