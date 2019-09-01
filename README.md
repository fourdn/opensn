## Open Social Network

### Git flow

#### Implementing new feature

You want to implement a new feature from our trello board, here is what you should do.

First of all, we should create a new branch from `dev` with desired feature like `feat/desired/1`.
Where feat is a type of branch, desired - feature name, 1 - card number from trello board.
All types of branch:
- feat
- test
- refactor
- docs

All features have to be tested and documented, so they have to go through all of these branch types.
It calls `development cycle`.

Also, don't forget to attach the github branch to the card from the trello board.

Note: you have to merge your feature branch to `dev` every time you want to create
a new branch, but another type.

#### Merging to master

When `development cycle` of the feature is done, you may merge `dev` to `master`.

### Build

#### Build docker container

```bash
docker build -t opensn .
```

#### Run docker container

```bash
docker run -it --rm -p 5432:5432 opensn
```
