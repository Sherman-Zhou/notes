1. not use AsyncTask, use AsyncTaskLoader instead
  -  LoaderManager.LoaderCallbacks<String[]> 
2. RecycleView
 - Adapter
 - ViewHolder
 - LayoutManager
    - LinearLayoutManager
    - GridLayoutManager
    - StaggeredGridLayoutManager
3. SP vs DP
4. Intents( activies navigation)
  - Explicit
  - implicit Intent (Common Intents)
5. Menu event
  - set menu Intent
  - onOptionsItemSelected
6. Lifecycle
  - onCreate(Created)
  - OnStart(Visible)
  - onResume(Active)
  - onPuase(Paused)
  - onStop(Stopped)
  - onDestroyed(Destroyed)
  - onRestart(Visible)
7. Permission
  - call Activity.requestPermissions
  - implment onRequestPermissionsResult

8. Data Persistence
   - onSavedInstanceState: key/value: complex value,  temporary 
   - SharedPreference: key/value: permitive values, 
   - SQLite
   - Internal/External Storage
   - Server
9. Content Provider
10. Service
  - StartService
  - Schedule
  - Bind

11. Databinding
12. I18N
13. Notifer
14. 