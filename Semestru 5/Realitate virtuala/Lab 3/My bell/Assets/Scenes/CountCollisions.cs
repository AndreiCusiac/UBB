using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CountCollisions : MonoBehaviour
{
    // Start is called before the first frame update

    public int collisionHits = 3;
    private int collisionCounter = 0;
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (collisionCounter >= collisionHits)
        {
            Destroy(gameObject);
        }
    }

    private void OnCollisionEnter(Collision collision)
    {
        Debug.Log("Collision detected");
            
        collisionCounter++;
        Debug.Log("counter:" + collisionCounter);
    }

    
}
