# base alpine/python image
FROM python:3.7-alpine
MAINTAINER jsh <jsh@ki-labs.com>
WORKDIR /app

# required ENVs
ENV LANG C.UTF-8

ADD . /app/

# install basic requirements
RUN apk add --no-cache curl bash nano git zsh util-linux
RUN sh -c "$(curl -fsSL https://raw.github.com/robbyrussell/oh-my-zsh/master/tools/install.sh)" > /dev/null

# install aws CLI
RUN pip3 install awscli --upgrade
ENV PATH $HOME/.local/bin:$PATH
RUN aws --version

# default entrypoint (for debugging)
ENV SHELL /bin/bash
ENTRYPOINT ["/bin/bash"]
