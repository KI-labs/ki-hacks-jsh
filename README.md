```
    _     _
   (_)___| |__
   | / __| '_ \
   | \__ \ | | |
  _/ |___/_| |_|
 |__/
```
# jsh

A solution to the ever-present problem of helping a colleague with a (relatively) clunky/inefficient terminal without proper tooling. JSH is *the* solution since it effectively allows for immediate ENV switching for quick help/debugging.

## Usage

Loading a specific user ENV is done as follows:

```shell script
jsh
```

## Installation

```bash
brew install ki-labs/jsh/jsh
```

Or 

```bash
brew tap KI-labs/jsh
brew install jsh
```


## Testing

- build "empty" environment

```shell script
 docker build -t empty:latest -f Dockerfile .
```

- set AWS ENVs
```shell script
export AWS_ACCESS_KEY_ID=
export AWS_SECRET_ACCESS_KEY=
export AWS_DEFAULT_REGION=
```

- instantiate "empty" environment
```shell script
docker run -it --rm -e USER=${USER} \
                -e AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
                -e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
                -e AWS_DEFAULT_REGION=$AWS_DEFAULT_REGION \
                empty:latest
```

- load "improved" environment (BASH)
    - alias (ll)
    - software (tree, fzf, autocompletion)
```
./jsh load platon.jshrc
```

- load "improved" environment (ZSH)
    - alias (jsh)
```
./jsh load josh.jshrc
```