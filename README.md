Objective:
You have been provided with two JSON files:

● The first JSON contains location identifiers (id), latitude, and longitude.
● The second JSON contains additional metadata related to each location identifier.

Process and Analyze Data:

● Load parse both JSON files and merge the data based on the matching id.
● Count how many valid points exist per type (e.g., restaurants, hotels, cafes, museums,
parks).
● Calculate the average rating per type, considering only valid entries.
● Identify the location with the highest number of reviews.
● Identify locations with incomplete data if any.


Endpoints (GET)
1.http://localhost:8080/locations/count-per-type
2.http://localhost:8080/locations/average-rating
3.http://localhost:8080/locations/highest-reviewed
4.http://localhost:8080/locations/incomplete
