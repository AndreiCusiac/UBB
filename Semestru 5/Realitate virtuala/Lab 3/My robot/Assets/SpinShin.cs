using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpinShin : MonoBehaviour
{
    // Start is called before the first frame update

    float v = 45.0f;

    private const float minAngle = -30.0f;
    private const float maxAngle = 30.0f;
    private float currentAngle = 0.0f;
    void Start()
    {
        
    }   

    // Update is called once per frame
    void Update()
    {
        float deltaAngle = Time.deltaTime * v;

        currentAngle += deltaAngle;

        if (currentAngle < minAngle || currentAngle > maxAngle)
        {
            v *= -1;
            deltaAngle *= -1;
            currentAngle += 2 * deltaAngle;
        }
        
        //transform.Rotate(Vector3.up, deltaAngle);
        //transform.Rotate(Vector3.right, deltaAngle);
        transform.Rotate(Vector3.forward, deltaAngle);
    }
}