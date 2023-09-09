using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
public class PlayerScore : MonoBehaviour
{
    public Transform playerTransform;
    public Transform gameObjectTransform;
    public Camera camera;
    public GameObject target;
    //public MeshRenderer renderer;
    public TMP_Text CurrentScoreTextMeshPro;
    public TMP_Text MaxScoreTextMeshPro;

    private string snake_level_1 = "snake_level_1";

    public float maxDistance = 10f;
    public float maxAngle = 30f;

    public int maxScore = 100;
    public int minScore = 0;

    private int currentScore = 0;
    private int allTimeHighScore = 0;
    private int highScore = 0;
    private int penalty = 2;

    private void Start()
    {
        currentScore = minScore;
        updateAllTimeHighScore();
    }

    private void updateAllTimeHighScore()
    {
        allTimeHighScore = GetPlayerPrefsInt(snake_level_1);
        MaxScoreTextMeshPro.text = allTimeHighScore.ToString();
    }

    private void Update()
    {
        // Calculate distance between player and game object
        float distance = Vector3.Distance(playerTransform.position, gameObjectTransform.position);
        
        //Debug.Log("Current distance: " + distance);

        // Calculate angle between player's forward vector and game object
        Vector3 playerToObject = gameObjectTransform.position - playerTransform.position;

        float angle = Vector3.Angle(playerTransform.forward, playerToObject);
        
        //Debug.Log("Current angle: " + angle);

        bool isTargetVisible = IsTargetVisible(camera, target);

        // Calculate score based on distance and gaze direction
        //currentScore = CalculateScore(distance, angle, isTargetVisible);
        
        currentScore += CalculateScore(distance, angle, isTargetVisible);

        updateScoresUI();

        updatePlayerPrefs();
    }

    private void updatePlayerPrefs()
    {
        if (highScore > allTimeHighScore)
        {
            SetPlayerPrefsInt(snake_level_1, highScore);
        }
    }

    private void updateScoresUI()
    {
        if (currentScore > maxScore)
        {
            currentScore = maxScore;
        }

        if (currentScore > highScore)
        {
            highScore = currentScore;
            CurrentScoreTextMeshPro.text = highScore.ToString();
        }
    }

    private int CalculateScore(float distance, float angle, bool isLookingAtObject)
    {
        int score = 0;
        
        if (isLookingAtObject)
        {
            if (distance <= maxDistance)
            {
                // Give more points if player is looking directly at the object
                float distanceFactor = 1f - (distance / maxDistance);
                float scorePercentage = distanceFactor;
                score = Mathf.RoundToInt(scorePercentage * (maxScore - minScore) + minScore);
            }
        }

        return score/10;
        return minScore; // No points if player is not looking at the object or outside scoring distance
    }
    
    bool IsTargetVisible(Camera c, GameObject go)
    {
        var planes = GeometryUtility.CalculateFrustumPlanes(c);
        var point = go.transform.position;
        foreach (var plane in planes)
        {
            if (plane.GetDistanceToPoint(point) < 0)
                return false;
        }
        return true;
    }
    
    public void SetPlayerPrefsInt(string KeyName, int Value)
    {
        PlayerPrefs.SetInt(KeyName, Value);
    }

    public int GetPlayerPrefsInt(string KeyName)
    {
        return PlayerPrefs.GetInt(KeyName);
    }
}