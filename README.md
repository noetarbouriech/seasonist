# seasonist

School Project – Microservices App for seasonal workers.

## Getting Started

First, get a local copy of the repository:

```bash
git clone https://github.com/noetarbouriech/seasonist.git
```

After that, install the dependencies:

```bash
make dependencies
```

Then, you can start the backend services with:

```bash
docker compose up -d
```

Finally, to get the app running, execute one of the following commands:

> If you want to start with the emulator:
> ```bash
> cd front/ && npm run run:android
> ```

> Or if you want to start with the [Expo go](https://play.google.com/store/apps/details?id=host.exp.exponent&hl=fr&gl=US) version (on your phone):
> ```bash
> cd front/ && npm run start
> ```
