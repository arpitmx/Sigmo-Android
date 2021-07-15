# Sigmo-Music 📱

Status : Under Development ⚒

Sigmo is an android app made for music aggregation and synced music on top of spotify!

#### See which songs your friends are listening! Connect with friends and listen together! Monthly analysis of listened songs and features like timeline and playbacktime of songs at a given day! Make room, invite , listen together!

# In-app walkthrough : 


https://user-images.githubusercontent.com/59350776/124971641-13895800-e047-11eb-81b9-56d4cf537776.mp4


<p float="left">

  <img src="https://github.com/arpitrmaurya/Sigmo-Music/blob/master/UI%20SS/photo_2021-07-08_23-35-13.jpg" width="150" />
  <img src="https://github.com/itsarpitr/Sigmo-Music/blob/master/UI%20SS/photo_2021-07-05_13-04-59.jpg" width="150" /> 
  <img src="https://github.com/itsarpitr/Sigmo-Music/blob/master/UI%20SS/photo_2021-07-05_13-05-00.jpg" width="150" />
</p>


## Icons meaning 👉 :  
                    🔹 (Partially done)
                    ✅ (Done)
                    👷‍♀️  (Building/Working on)-(Queue number - Lesser means more priority)
                    

## Improvements and Additions 💹:


### Open issues 🔴:

1. Online status not updating sometimes
2. When sigmo is closed without pausing the song from the Spotify , 
    the next time it will show playing as ísPlaying wasn't updated when the app got closed
3. Discs will stop rotating when the recycler view is slided down due to reassiging of the items in the viewholders and the rotation animation is not applied when        the new item is assigned
4. Songs won't update untill a restart 'sometimes'.

--------------------------------------------------------------------------------------------------------------------------

### Ideas for Future Additions ➕ : 

1. Use Spotify SDK for song thumbnails , song name and other song respective details 🔹
2. Use Spotify SDK for miniplayer updation. 🔹
3. Adaptive themes : changes theme according to light conditions
4. Initiate the apis and services during the splash screen
5. Add lyrics feature into dropdown sheet
6. Downloadable music
7. A mini podcast creator , voice recorder
8. Users like to slide downwards instead of sideways , slide down to change song like resso.
9. Add features like vocolloco
10. Adding comments on a particular song, so if a friend have left some comment on a particular song , when the user plays that same song comment will b visible
11. Add more analytics of a song through spotify web api 👷‍- 2
12. Explore page in which recommemended songs will be streamed for a playback of 30 seconds (sort of gives an idea of a song without fully listening) 👷‍♀️ - 3 
13. Rooms for synced listening 👷‍ - 1
-----------

### Logic Improvements :

1. When updates occurs to the database whole list is reloaded with content for example everytime a new songs loads


### Database and Backend Related :

1. Use Room persistent Database
2. Implement MVVM and seperate UI and business logics from activities and fragments
3. 

-----------

### UX/UI Improvements :

1. Speak human.
2. Remove equilizer gif , annoying
3. Show rotating of discs only to the songs which users plays of the friends fragment
4. Make bottom navigation hidable when scrolled down






