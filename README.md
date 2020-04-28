# Parkinglot manager


A simple parking lot manager that implements the following requirements:

A toll parking contains multiple parking slots of different types :
- the standard parking slots for gasoline powered cars
- parking slots with 20kw power supply for electric cars
- parking slots with 50kw power supply for electric cars

20kw electric cars cannot use 50kw power supplies and vice-versa.

Every Parking is free to implement is own pricing policy :
- Some only bills their customer for each hour spent in the parking (nb hours * hour price)
- Some other bill a fixed amount + each hour spent in the parking (fixed amount + nb hours * hour price)
- Other policies are implementable by a new using the enum in the REST API. Values can be easily reconfigured via the API. 

Cars of all types come in and out randomly, the API must :
- Send them to the right parking slot of refuse them if there is no slot (of the right type) left.
- Mark the parking slot as Free when the car leaves it
- Bill the customer when the car leaves.

# HOW TO BUILD & RUN

- To build run
```ssh
./mvnw clean install
```
- To start run 
```ssh
./mvnw spring-boot:run
```

# HOW TO USE THE API

The API is designed using swagger 2.0 and can be interracted with individually by using https://editor.swagger.io/ with the provided /src/main/resources/swagger/ParkingManager.yaml

There are 2 types of operations. One for the manager of the parking lot(s) and one for the client accessing the parking lot. 

All the examples here will be shown with localhost.

## Retrieve all parking lots

- The manager(s) can fetch all the existing parking lots using the following request:
```ssh
curl -X GET "localhost:8080/parkinglotmanager/parkinglots" -H "accept: application/json"
``` 
- If the request is successful, the response will be something like this:
```json
[
  {
    "description": "Nice Centre",
    "code": "NCE"
  }
]
```

## Create a parking lot
- The manager can create a parking lot with a unique code by using the following request:
```ssh
curl -X POST "http://editor.swagger.io/parkinglotmanager/parkinglot/NCE" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"description\": \"Nice Centre\", \"numberOfStandardParkingSlots\": 0, \"numberOf20KWParkingSlots\": 0, \"numberOf50KWParkingSlots\": 0, \"pricingPolicy\": { \"policy\": \"perHour\", \"basePrice\": 0, \"fixedAmmount\": 0 }, \"version\": 0}"
```

This can also create a new pricing policy if the requested one does not exist.

- If the request is successful, the response will be something like this:
```json
{
  "description": "Nice Centre",
  "numberOfStandardParkingSlots": 0,
  "numberOf20KWParkingSlots": 0,
  "numberOf50KWParkingSlots": 0,
  "pricingPolicy": {
    "policy": "perHour",
    "basePrice": 0,
    "fixedAmmount": 0
  },
  "version": 0
}
```

## Retrieve parking lot by code

- The manager(s) can fetch one parking lot by it's unique code using:
```ssh
curl -X GET "http://editor.swagger.io/parkinglotmanager/parkinglot/NCE" -H "accept: application/json"
```
- If the request is successful, the response will be something like this:
```json
{
  "description": "Nice Centre",
  "numberOfStandardParkingSlots": 0,
  "numberOf20KWParkingSlots": 0,
  "numberOf50KWParkingSlots": 0,
  "pricingPolicy": {
    "policy": "perHour",
    "basePrice": 0,
    "fixedAmmount": 0
  },
  "version": 0
}
```
## Update parking lot by code
- The manager(s) can update the pricing policy of the parking lot by using the following query:
```ssh
curl -X PUT "http://editor.swagger.io/parkinglotmanager/parkinglot/NCE" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"pricingPolicy\": { \"policy\": \"perHour\", \"basePrice\": 0, \"fixedAmmount\": 0 }, \"version\": 0}"
```
- If the request is successful, the response will be something like this:
```json
{
  "pricingPolicy": {
    "policy": "perHour",
    "basePrice": 0,
    "fixedAmmount": 0
  },
  "version": 1
}
```
- After each update, the version of the parking lot is bumped to ensure consistency. The next update should provide the corresponding version otherwise an error can occur.

## Delete the parking lot by code
- The manager(s) can delete the parking lot providing the code and the correct version:
```ssh
curl -X DELETE "http://editor.swagger.io/parkinglotmanager/parkinglot/NCE?version=1" -H "accept: application/json"
```
- There is no body request for the delete and the answer will only be 200 if the operation is successful. The parking lot delete also deletes every car that is stored within.

## Create a parking lot entry
- A new car can request entry in the parking lot by using the following query:
```ssh
curl -X POST "http://editor.swagger.io/parkinglotmanager/parkinglot/NCE/carParked/BB123EQ?carType=gasoline" -H "accept: application/json"
```
- If the request is successful the response will be something like:
```json
{
  "carID": "BB123EQ",
  "assignedSlot": "G10",
  "entryTime": "string"
}
```

## Delete the parking lot entry
- An existing car can request exit from the parking lot by using the following query:
```ssh
curl -X DELETE "http://editor.swagger.io/parkinglotmanager/parkinglot/NCE/carParked/BB123EQ" -H "accept: application/json"
```
- If the request is successful the response will be something like:
```json
{
  "carID": "BB123EQ",
  "assignedSlot": "G10",
  "sumToPay": 32.5
}
```

## Possible errors
- The parking lot with the given code was not found"
- The parking lot with the given code already exists"
- The car with the given plate was not found"),
- The car with the given plate already exists or is in another parking lot
- There are not free slots for this car type
- Unknown type of car
- UUnknown pricing policy
- The version provided is not equal to the persisted version
- Invalid Parking lot code
Invalid car plate