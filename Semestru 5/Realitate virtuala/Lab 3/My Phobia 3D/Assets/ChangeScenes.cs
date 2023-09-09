using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class ChangeScenes : MonoBehaviour
{
    // Start is called before the first frame update

    private List<string> sceneNames = new List<string>();

    void Start()
    {
        sceneNames.Add("Level1");
        sceneNames.Add("Level2");
        sceneNames.Add("Level3");
        sceneNames.Add("Level4");
        sceneNames.Add("Level5");
        sceneNames.Add("Level6");
        sceneNames.Add("Level7");
        sceneNames.Add("Level8");
        sceneNames.Add("Level9");
        
        var dropdown = transform.GetComponent<TMP_Dropdown>();
        
        dropdown.options.Clear();

        List<string> items = new List<string>();
        
        items.Add("Alege:");
        items.Add("Nivel 1");
        items.Add("Nivel 2");
        items.Add("Nivel 3");
        items.Add("Nivel 4");
        items.Add("Nivel 5");
        items.Add("Nivel 6");
        items.Add("Nivel 7");
        items.Add("Nivel 8");
        items.Add("Nivel 9");

        foreach (var item in items)
        {
            dropdown.options.Add(new TMP_Dropdown.OptionData() {text = item});
        }
        
        dropdown.onValueChanged.AddListener(delegate { DropdownItemSelected(dropdown); });
    }

    void DropdownItemSelected(TMP_Dropdown dropdown)
    {
        int index = dropdown.value;
        int selectedScene = index - 1;

        UnityEngine.SceneManagement.SceneManager.LoadScene(sceneNames[selectedScene]);
    }
    
    //SceneManager.LoadScene(name);
}
