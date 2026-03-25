{
  "startPoint": {
    "x": 57.654545454545456,
    "y": 11.218181818181828,
    "heading": "linear",
    "startDeg": 90,
    "endDeg": 180,
    "locked": false
  },
  "lines": [
    {
      "id": "mkeam3ob-l70atg",
      "name": "LeftArtifact",
      "endPoint": {
        "x": 25.509090909090908,
        "y": 35.763636363636365,
        "heading": "tangential",
        "reverse": false
      },
      "controlPoints": [
        {
          "x": 58.23636363636364,
          "y": 34.01818181818184
        }
      ],
      "color": "#59BB78",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkeapxgn-4q6r7f",
      "name": "ShootLeft",
      "endPoint": {
        "x": 58.90909090909093,
        "y": 20.34545454545453,
        "heading": "linear",
        "reverse": true,
        "startDeg": 180,
        "endDeg": 116
      },
      "controlPoints": [],
      "color": "#DA588D",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkeatg1t-hacjp6",
      "name": "PrepareZone",
      "endPoint": {
        "x": 10.163636363636346,
        "y": 8.854545454545464,
        "heading": "linear",
        "reverse": false,
        "startDeg": 116,
        "endDeg": 180
      },
      "controlPoints": [
        {
          "x": 90.54545454545452,
          "y": 18.509090909090894
        }
      ],
      "color": "#A99A96",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkeay6e8-wgy220",
      "name": "ShootZone",
      "endPoint": {
        "x": 57.33636363636364,
        "y": 18.318181818181817,
        "heading": "linear",
        "reverse": false,
        "startDeg": 180,
        "endDeg": 121
      },
      "controlPoints": [],
      "color": "#69A566",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkk232lk-d6ufk1",
      "name": "GateZone",
      "endPoint": {
        "x": 10.309090909090916,
        "y": 13.640508427742477,
        "heading": "linear",
        "reverse": false,
        "startDeg": 121,
        "endDeg": 180
      },
      "controlPoints": [
        {
          "x": 73.48181818181821,
          "y": 29.78636363636363
        }
      ],
      "color": "#59B66A",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkk23uzj-8ft7ci",
      "name": "GateShoot",
      "endPoint": {
        "x": 55.418181818181814,
        "y": 17.309090909090905,
        "heading": "linear",
        "reverse": false,
        "startDeg": 180,
        "endDeg": 121
      },
      "controlPoints": [],
      "color": "#B95997",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkl48ku4-e0quna",
      "name": "GateZone2",
      "endPoint": {
        "x": 10.993920972644373,
        "y": 13.735562310030389,
        "heading": "linear",
        "reverse": false,
        "startDeg": 121,
        "endDeg": 180
      },
      "controlPoints": [],
      "color": "#755CD9",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkl49jle-z2qcgo",
      "name": "GateShoot2",
      "endPoint": {
        "x": 64.51671732522796,
        "y": 17.009118541033445,
        "heading": "linear",
        "reverse": false,
        "startDeg": 180,
        "endDeg": 180
      },
      "controlPoints": [],
      "color": "#CCA6AB",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkk248bk-tbeba7",
      "name": "Park",
      "endPoint": {
        "x": 47,
        "y": 18.254545454545458,
        "heading": "linear",
        "reverse": false,
        "startDeg": 180,
        "endDeg": 180
      },
      "controlPoints": [],
      "color": "#6D658C",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    }
  ],
  "shapes": [
    {
      "id": "triangle-1",
      "name": "Red Goal",
      "vertices": [
        {
          "x": 144,
          "y": 70
        },
        {
          "x": 144,
          "y": 144
        },
        {
          "x": 120,
          "y": 144
        },
        {
          "x": 138,
          "y": 119
        },
        {
          "x": 138,
          "y": 70
        }
      ],
      "color": "#dc2626",
      "fillColor": "#ff6b6b"
    },
    {
      "id": "triangle-2",
      "name": "Blue Goal",
      "vertices": [
        {
          "x": 6,
          "y": 119
        },
        {
          "x": 25,
          "y": 144
        },
        {
          "x": 0,
          "y": 144
        },
        {
          "x": 0,
          "y": 70
        },
        {
          "x": 7,
          "y": 70
        }
      ],
      "color": "#2563eb",
      "fillColor": "#60a5fa"
    }
  ],
  "sequence": [
    {
      "kind": "path",
      "lineId": "mkeam3ob-l70atg"
    },
    {
      "kind": "path",
      "lineId": "mkeapxgn-4q6r7f"
    },
    {
      "kind": "path",
      "lineId": "mkeatg1t-hacjp6"
    },
    {
      "kind": "path",
      "lineId": "mkeay6e8-wgy220"
    },
    {
      "kind": "path",
      "lineId": "mkk232lk-d6ufk1"
    },
    {
      "kind": "path",
      "lineId": "mkk23uzj-8ft7ci"
    },
    {
      "kind": "path",
      "lineId": "mkl48ku4-e0quna"
    },
    {
      "kind": "path",
      "lineId": "mkl49jle-z2qcgo"
    },
    {
      "kind": "path",
      "lineId": "mkk248bk-tbeba7"
    }
  ],
  "settings": {
    "xVelocity": 75,
    "yVelocity": 65,
    "aVelocity": 3.141592653589793,
    "kFriction": 0.1,
    "rWidth": 16.4566929134,
    "rHeight": 17.5196850394,
    "safetyMargin": 1,
    "maxVelocity": 40,
    "maxAcceleration": 30,
    "maxDeceleration": 30,
    "fieldMap": "decode.webp",
    "robotImage": "/robot.png",
    "theme": "auto",
    "showGhostPaths": false,
    "showOnionLayers": false,
    "onionLayerSpacing": 3,
    "onionColor": "#dc2626",
    "onionNextPointOnly": false
  },
  "version": "1.2.1",
  "timestamp": "2026-01-20T20:32:51.678Z"
}