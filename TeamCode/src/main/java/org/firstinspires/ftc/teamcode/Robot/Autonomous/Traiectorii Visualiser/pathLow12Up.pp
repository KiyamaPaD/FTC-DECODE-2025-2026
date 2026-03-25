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
      "id": "mkk3chwh-6360jz",
      "name": "MiddleZone",
      "endPoint": {
        "x": 26.422680412371147,
        "y": 59.896907216494846,
        "heading": "linear",
        "reverse": false,
        "startDeg": 116,
        "endDeg": 180
      },
      "controlPoints": [
        {
          "x": 82.24545454545455,
          "y": 37.127272727272725
        }
      ],
      "color": "#B8B9D6",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkk3dtd8-h562av",
      "name": "MiddleShoot",
      "endPoint": {
        "x": 61.28865979381443,
        "y": 82.65979381443299,
        "heading": "linear",
        "reverse": false,
        "startDeg": 180,
        "endDeg": 180
      },
      "controlPoints": [],
      "color": "#A6A697",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkl43fas-k64u8e",
      "name": "UpArtifacts",
      "endPoint": {
        "x": 25.33130699088146,
        "y": 83.46504559270514,
        "heading": "linear",
        "reverse": false,
        "startDeg": 180,
        "endDeg": 180
      },
      "controlPoints": [],
      "color": "#97D68D",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkl44m4o-qrvqys",
      "name": "UpShoot",
      "endPoint": {
        "x": 60.270516717325236,
        "y": 79.42857142857144,
        "heading": "linear",
        "reverse": false,
        "startDeg": 180,
        "endDeg": 180
      },
      "controlPoints": [],
      "color": "#5CDB57",
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": ""
    },
    {
      "id": "mkk248bk-tbeba7",
      "name": "Park",
      "endPoint": {
        "x": 41.32596063730084,
        "y": 82.27516401124649,
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
      "lineId": "mkk3chwh-6360jz"
    },
    {
      "kind": "path",
      "lineId": "mkk3dtd8-h562av"
    },
    {
      "kind": "path",
      "lineId": "mkl43fas-k64u8e"
    },
    {
      "kind": "path",
      "lineId": "mkl44m4o-qrvqys"
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
  "timestamp": "2026-01-19T12:01:16.104Z"
}