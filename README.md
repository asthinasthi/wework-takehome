# Take Home Assignment

## To run the application

1. Goto `run_fat_jar` folder
2. `java -jar fat.jar`
3. Wait for program to complete
4. Check `output.json` for results

## Description
- Input file at `https://s3.amazonaws.com/fieldlens-public/urls.txt` is read.
- The urls are loaded to a concurrent Blocking Queue
- Worker.java is the worker thread which will dequeue a "job" from the queue
- Search Terms `["web", "security", "was", "anirudh", "mathad"]` are searched on every webpage using Jsoup
- For every search term a boolean flag is updated if found
- If the webpage was not loaded error message is updated
- Sample output
```
{
  "url": "\"youtu.be/\"",
  "rank": 21,
  "sessions": 1,
  "searchTermExists": {},
  "executedBy": "Thread: 15",
  "msg": "Error accessing the Web Page"
},
{
  "url": "\"stumbleupon.com/\"",
  "rank": 25,
  "sessions": 1,
  "searchTermExists": {
    "security": "false",
    "web": "true",
    "mathad": "false",
    "was": "false",
    "anirudh": "false"
  },
  "executedBy": "Thread: 16"
}
```
- Optional: Can set num of reattempts by updating field `MAX_SESSIONS` in Application.java
- In case run fails, please check the `local_run_fat_jar` folder for my results, when I ran the application locally.
- Log files are available in the output.log