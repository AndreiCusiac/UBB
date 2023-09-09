using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spin : MonoBehaviour
{
    // Start is called before the first frame update

    float v = 45.0f;
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        float deltaAngle = Time.deltaTime * v;
        transform.Rotate(Vector3.up, deltaAngle);
    }
}
