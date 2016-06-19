CC=g++
CFLAGS= -g -Wall
TARGET=i2c
all: $(TARGET)
$(TARGET): $(TARGET).cpp
	$(CC) $(CFLAGS) -o $(TARGET) $(TARGET).cpp -lcurl

clean:
	$(RM) $(TARGET)
