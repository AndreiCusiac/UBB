using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BeeMove : MonoBehaviour
{
    private Vector3 _startPosition;
    private Vector3 _newPosition;
    
    private const float range = 7.0f;
    
    // Start is called before the first frame update
    void Start()
    {
        _startPosition = transform.position;
    }

    // Update is called once per frame
    void Update()
    {
        // transform.position = _startPosition + new Vector3(Mathf.Sin(Time.time), 0.0f, 0.0f);
        
        _newPosition = transform.position;
        _newPosition.x += range * Mathf.Sin(Time.time) * Time.deltaTime;
        transform.position = _newPosition;
    }
}
