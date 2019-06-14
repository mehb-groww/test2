FROM python:3.6
ADD . /code
WORKDIR /code
RUN pip3 install -r ./requirement.txt
RUN chmod 644 simpleFlask.py
RUN apt-get -y update
RUN apt-get -y install net-tools
RUN apt-get -y install tcpdump
CMD python3 simpleFlask.py
