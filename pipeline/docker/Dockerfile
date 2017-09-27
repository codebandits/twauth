FROM ubuntu:latest

RUN apt-get update \
    && apt-get install -y curl unzip bzip2 xvfb openjdk-8-jdk lsb-release build-essential \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server \
    && curl -s https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && echo "deb http://packages.cloud.google.com/apt cloud-sdk-$(lsb_release -c -s) main" > /etc/apt/sources.list.d/google-cloud-sdk.list \
    && apt-get update && apt-get install -y google-chrome-stable google-cloud-sdk \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN CHROMEDRIVER_VERSION=$(curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE) \
    && mkdir -p /opt/chromedriver-$CHROMEDRIVER_VERSION \
    && curl -sS -o /tmp/chromedriver_linux64.zip http://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip \
    && unzip -qq /tmp/chromedriver_linux64.zip -d /opt/chromedriver-$CHROMEDRIVER_VERSION \
    && rm /tmp/chromedriver_linux64.zip \
    && chmod +x /opt/chromedriver-$CHROMEDRIVER_VERSION/chromedriver \
    && ln -fs /opt/chromedriver-$CHROMEDRIVER_VERSION/chromedriver /usr/local/bin/chromedriver

ENV CI=true
ENV TERM=dumb
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

RUN usermod -d /var/lib/mysql mysql
RUN useradd browsertests --create-home --shell /bin/bash
