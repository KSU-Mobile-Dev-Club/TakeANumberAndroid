package club.dev.mobile.ksu.takeanumber.UI;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import club.dev.mobile.ksu.takeanumber.R;

/**
 * A PreferenceActivity that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        // Display the fragment as the main content.
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction().replace(R.id.preference_content, new SettingsFragment()).commit();
    }

    /**
     * Set up the {@link ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * The settings fragment for this activity. Displays a list of settings and allows them to be
     * clicked on to modify them using a PreferenceFragment.
     */

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

            Preference userNamePreference = findPreference("key_user_name");
            userNamePreference.setSummary(sharedPreferences.getString("key_user_name", ""));

            Preference userIdPreference = findPreference("key_user_id");
            userIdPreference.setSummary(sharedPreferences.getString("key_user_id", ""));
        }
    }
}
