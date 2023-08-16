import json
import re


def is_valid_time(time_string):
    pattern = r'^([01]\d|2[0-3]):([0-5]\d)$'
    return re.match(pattern, time_string) is not None


def main():
    error_log = {
        "bus_id": 0,
        "stop_id": 0,
        "stop_name": 0,
        "next_stop": 0,
        "stop_type": 0,
        "a_time": 0
    }

    data_1 = """
        [
            {
                "bus_id": 128,
                "stop_id": 1,
                "stop_name": "Prospekt Av.",
                "next_stop": 3,
                "stop_type": "S",
                "a_time": "08:12"
            },
            {
                "bus_id": 128,
                "stop_id": 3,
                "stop_name": "Elm Street",
                "next_stop": 5,
                "stop_type": "",
                "a_time": "8:19"
            },
            {
                "bus_id": 128,
                "stop_id": 5,
                "stop_name": "Fifth Avenue",
                "next_stop": 7,
                "stop_type": "OO",
                "a_time": "08:25"
            },
            {
                "bus_id": 128,
                "stop_id": 7,
                "stop_name": "Sesame Street",
                "next_stop": 0,
                "stop_type": "F",
                "a_time": "08:77"
            },
            {
                "bus_id": 256,
                "stop_id": 2,
                "stop_name": "Pilotow Street",
                "next_stop": 3,
                "stop_type": "S",
                "a_time": "09:20"
            },
            {
                "bus_id": 256,
                "stop_id": 3,
                "stop_name": "Elm",
                "next_stop": 6,
                "stop_type": "",
                "a_time": "09:45"
            },
            {
                "bus_id": 256,
                "stop_id": 6,
                "stop_name": "Sunset Boulevard",
                "next_stop": 7,
                "stop_type": "A",
                "a_time": "09:59"
            },
            {
                "bus_id": 256,
                "stop_id": 7,
                "stop_name": "Sesame Street",
                "next_stop": 0,
                "stop_type": "F",
                "a_time": "10.12"
            },
            {
                "bus_id": 512,
                "stop_id": 4,
                "stop_name": "bourbon street",
                "next_stop": 6,
                "stop_type": "S",
                "a_time": "38:13"
            },
            {
                "bus_id": 512,
                "stop_id": 6,
                "stop_name": "Sunset Boulevard",
                "next_stop": 0,
                "stop_type": "F",
                "a_time": "08:16"
            }
                        {
                "bus_id": 128,
                "stop_id": 1,
                "stop_name": "Prospekt Av.",
                "next_stop": 3,
                "stop_type": "S",
                "a_time": "08:12"
            },
            {
                "bus_id": 128,
                "stop_id": 3,
                "stop_name": "Elm Street",
                "next_stop": 5,
                "stop_type": "",
                "a_time": "8:19"
            },
            {
                "bus_id": 128,
                "stop_id": 5,
                "stop_name": "Fifth Avenue",
                "next_stop": 7,
                "stop_type": "OO",
                "a_time": "08:25"
            },
            {
                "bus_id": 128,
                "stop_id": 7,
                "stop_name": "Sesame Street",
                "next_stop": 0,
                "stop_type": "F",
                "a_time": "08:77"
            },
            {
                "bus_id": 256,
                "stop_id": 2,
                "stop_name": "Pilotow Street",
                "next_stop": 3,
                "stop_type": "S",
                "a_time": "09:20"
            },
            {
                "bus_id": 256,
                "stop_id": 3,
                "stop_name": "Elm",
                "next_stop": 6,
                "stop_type": "",
                "a_time": "09:45"
            },
            {
                "bus_id": 256,
                "stop_id": 6,
                "stop_name": "Sunset Boulevard",
                "next_stop": 7,
                "stop_type": "A",
                "a_time": "09:59"
            },
            {
                "bus_id": 256,
                "stop_id": 7,
                "stop_name": "Sesame Street",
                "next_stop": 0,
                "stop_type": "F",
                "a_time": "10.12"
            },
            {
                "bus_id": 512,
                "stop_id": 4,
                "stop_name": "bourbon street",
                "next_stop": 6,
                "stop_type": "S",
                "a_time": "38:13"
            },
            {
                "bus_id": 512,
                "stop_id": 6,
                "stop_name": "Sunset Boulevard",
                "next_stop": 0,
                "stop_type": "F",
                "a_time": "08:16"
            }
        ]
        """

    data_2 = """
        [
            {
                "bus_id" : 128, 
                "stop_id" : 1, 
                "stop_name" : 
                "Fifth Avenue", 
                "next_stop" : 4, 
                "stop_type" : "S", 
                "a_time" : "08:12"
            },
            {
                "bus_id" : 128, 
                "stop_id" : 4, 
                "stop_name" : 
                "Abbey Road", 
                "next_stop" : 5, 
                "stop_type" : "", 
                "a_time" : "08:19"
            },
            {
                "bus_id" : 128, 
                "stop_id" : 5, 
                "stop_name" : "Santa Monica Boulevard", 
                "next_stop" : 8, 
                "stop_type" : "O", 
                "a_time" : "08:25"
            },
            {
                "bus_id" : 128, 
                "stop_id" : 8, 
                "stop_name" : "Elm Street", 
                "next_stop" : 11, 
                "stop_type" : "", 
                "a_time" : "08:37"
            },
            {   
                "bus_id" : 128, 
                "stop_id" : 11, 
                "stop_name" : "Beale Street", 
                "next_stop" : 12, 
                "stop_type" : "", 
                "a_time" : "09:20"
            },
            {   
                "bus_id" : 128, 
                "stop_id" : 12, 
                "stop_name" : "Sesame Street", 
                "next_stop" : 14, 
                "stop_type" : "", 
                "a_time" : "09:45"
            },
            {   
                "bus_id" : 128, 
                "stop_id" : 14, 
                "stop_name" : "Bourbon Street", 
                "next_stop" : 19, 
                "stop_type" : "O",
                "a_time" : "09:59"
            },
            {   
                "bus_id" : 128, 
                "stop_id" : 19, 
                "stop_name" : "Prospekt Avenue", 
                "next_stop" : 0, 
                "stop_type" : "F", 
                "a_time" : "10:12"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 2, 
                "stop_name" : "Pilotow Street", 
                "next_stop" : 3, 
                "stop_type" : "S", 
                "a_time" : "08:13"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 3, 
                "stop_name" : "Startowa Street", 
                "next_stop" : 8, 
                "stop_type" : "", 
                "a_time" : "08:16"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 8, 
                "stop_name" : "Elm Street", 
                "next_stop" : 10, 
                "stop_type" : "", 
                "a_time" : "08:29"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 10, 
                "stop_name" : "Lombard Street", 
                "next_stop" : 12, 
                "stop_type" : "", 
                "a_time" : "08:44"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 12, 
                "stop_name" : "Sesame Street", 
                "next_stop" : 13, 
                "stop_type" : "O", 
                "a_time" : "08:46"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 13, 
                "stop_name" : "Orchard Road", 
                "next_stop" : 16, 
                "stop_type" : "", 
                "a_time" : "09:13"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 16, 
                "stop_name" : "Sunset Boulevard", 
                "next_stop" : 17, 
                "stop_type" : "O", 
                "a_time" : "09:26"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 17, 
                "stop_name" : "Khao San Road", 
                "next_stop" : 20, 
                "stop_type" : "O", 
                "a_time" : "10:25"
            },
            {   
                "bus_id" : 256, 
                "stop_id" : 20, 
                "stop_name" : "Michigan Avenue", 
                "next_stop" : 0, 
                "stop_type" : "F", 
                "a_time" : "11:26"
            },
            {   
                "bus_id" : 512, 
                "stop_id" : 6, 
                "stop_name" : "Arlington Road", 
                "next_stop" : 7, 
                "stop_type" : "S", 
                "a_time" : "11:06"
            },
            {   
                "bus_id" : 512, 
                "stop_id" : 7, 
                "stop_name" : "Parizska Street", 
                "next_stop" : 8, 
                "stop_type" : "", 
                "a_time" : "11:15"
            },
            {   
                "bus_id" : 512, 
                "stop_id" : 8, 
                "stop_name" : "Elm Street", 
                "next_stop" : 9, 
                "stop_type" : "", 
                "a_time" : "11:56"
            },
            {   
                "bus_id" : 512, 
                "stop_id" : 9, 
                "stop_name" : "Niebajka Avenue", 
                "next_stop" : 15, 
                "stop_type" : "", 
                "a_time" : "12:20"
            },
            {   
                "bus_id" : 512, 
                "stop_id" : 15, 
                "stop_name" : "Jakis Street", 
                "next_stop" : 16, 
                "stop_type" : "", 
                "a_time" : "12:44"
            },
            {   
                "bus_id" : 512, 
                "stop_id" : 16, 
                "stop_name" : "Sunset Boulevard", 
                "next_stop" : 18, 
                "stop_type" : "", 
                "a_time" : "13:01"
            },
            {   
                "bus_id" : 512, 
                "stop_id" : 18, 
                "stop_name" : "Jakas Avenue", 
                "next_stop" : 19, 
                "stop_type" : "", 
                "a_time" : "14:00"
            },
            {   
                "bus_id" : 1024, 
                "stop_id" : 21, 
                "stop_name" : "Karlikowska Avenue", 
                "next_stop" : 12, 
                "stop_type" : "S", 
                "a_time" : "13:01"
            },
            {   
                "bus_id" : 1024, 
                "stop_id" : 12, 
                "stop_name" : "Sesame Street", 
                "next_stop" : 0, 
                "stop_type" : "F", 
                "a_time" : "14:00"
            },
            {   
                "bus_id" : 512, 
                "stop_id" : 19, 
                "stop_name" : "Prospekt Avenue", 
                "next_stop" : 0, 
                "stop_type" : "F", 
                "a_time" : "14:11"
            }
        ]
        """

    data_3 = """
    [
        {
            "bus_id" : 128, 
            "stop_id" : 1, 
            "stop_name" : "Fifth Avenue", 
            "next_stop" : 4, 
            "stop_type" : "S", 
            "a_time" : "08:12"
        },
        {
            "bus_id" : 128, 
            "stop_id" : 4, 
            "stop_name" : "abbey Road", 
            "next_stop" : 5, 
            "stop_type" : "FF", 
            "a_time" : "08:19"
        },
        {   "bus_id" : 128, 
            "stop_id" : 5, 
            "stop_name" : "Santa Monica Boulevard", 
            "next_stop" : 8, 
            "stop_type" : "O", 
            "a_time" : "two"
        },
        {   
            "bus_id" : 128, 
            "stop_id" : 8, 
            "stop_name" : "Elm Street Str.", 
            "next_stop" : 11, 
            "stop_type" : "", 
            "a_time" : "08:37"
        },
        {  
            "bus_id" : 128, 
            "stop_id" : 11, 
            "stop_name" : "Beale Street", 
            "next_stop" : 12, 
            "stop_type" : "", 
            "a_time" : "39:20"
        },
        {
            "bus_id" : 128, 
            "stop_id" : 12, 
            "stop_name" : "Sesame Street", 
            "next_stop" : 14, 
            "stop_type" : "", 
            "a_time" : "09:95"
        },
        {
            "bus_id" : 128, 
            "stop_id" : 14, 
            "stop_name" : "Bourbon street", 
            "next_stop" : 19, 
            "stop_type" : "O", 
            "a_time" : "09:59"
        },
        {
            "bus_id" : 128, 
            "stop_id" : 19, 
            "stop_name" : "Avenue", 
            "next_stop" : 0, 
            "stop_type" : "F", 
            "a_time" : "10:12"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 2, 
            "stop_name" : "Pilotow Street", 
            "next_stop" : 3, 
            "stop_type" : "S", 
            "a_time" : "08.13"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 3, 
            "stop_name" : "Startowa Street", 
            "next_stop" : 8, 
            "stop_type" : "d", 
            "a_time" : "08:16"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 8, 
            "stop_name" : "Elm", 
            "next_stop" : 10, 
            "stop_type" : "", 
            "a_time" : "08:29"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 10, 
            "stop_name" : "Lombard Street", 
            "next_stop" : 12, 
            "stop_type" : "", 
            "a_time" : "08;44"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 12, 
            "stop_name" : "Sesame Street", 
            "next_stop" : 13, 
            "stop_type" : "O", 
            "a_time" : "08:46"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 13, 
            "stop_name" : "Orchard Road", 
            "next_stop" : 16, 
            "stop_type" : "", 
            "a_time" : "09:13"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 16, 
            "stop_name" : "Sunset Boullevard", 
            "next_stop" : 17, 
            "stop_type" : "O", 
            "a_time" : "09:26"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 17, 
            "stop_name" : "Khao San Road", 
            "next_stop" : 20, 
            "stop_type" : "o", 
            "a_time" : "10:25"
        },
        {   
            "bus_id" : 256, 
            "stop_id" : 20, 
            "stop_name" : "Michigan Avenue", 
            "next_stop" : 0, 
            "stop_type" : "F", 
            "a_time" : "11:26"
        },
        {   
            "bus_id" : 512, 
            "stop_id" : 6, 
            "stop_name" : "Arlington Road", 
            "next_stop" : 7, 
            "stop_type" : "s", 
            "a_time" : "11:06"
        },
        {   
            "bus_id" : 512, 
            "stop_id" : 7, 
            "stop_name" : "Parizska St.", 
            "next_stop" : 8, 
            "stop_type" : "", 
            "a_time" : "11:15"
        },
        {
            "bus_id" : 512, 
            "stop_id" : 8, 
            "stop_name" : "Elm Street", 
            "next_stop" : 9, 
            "stop_type" : "", 
            "a_time" : "11:76"
        },
        {   
            "bus_id" : 512, 
            "stop_id" : 9, 
            "stop_name" : "Niebajka Av.", 
            "next_stop" : 15, 
            "stop_type" : "", 
            "a_time" : "12:20"
        },
        {   
            "bus_id" : 512, 
            "stop_id" : 15, 
            "stop_name" : "Jakis Street", 
            "next_stop" : 16, 
            "stop_type" : "", 
            "a_time" : "12:44"
        },
        {   
            "bus_id" : 512, 
            "stop_id" : 16, 
            "stop_name" : "Sunset Boulevard", 
            "next_stop" : 18, 
            "stop_type" : "", 
            "a_time" : "13:01"
        },
        {   
            "bus_id" : 512, 
            "stop_id" : 18, 
            "stop_name" : "Jakas Avenue", 
            "next_stop" : 19, 
            "stop_type" : "", 
            "a_time" : "14:00"
        },
        {   
            "bus_id" : 1024, 
            "stop_id" : 21, 
            "stop_name" : "Karlikowska Avenue", 
            "next_stop" : 12, 
            "stop_type" : "S", 
            "a_time" : "13:01"
        },
        {   
            "bus_id" : 1024, 
            "stop_id" : 12, 
            "stop_name" : "Sesame Street", 
            "next_stop" : 0, 
            "stop_type" : "F", 
            "a_time" : "14:00:00"
        },
        {   
            "bus_id" : 1024, 
            "stop_id" : 19, 
            "stop_name" : "Prospekt Avenue", 
            "next_stop" : 0, 
            "stop_type" : "F", 
            "a_time" : "14:11"
    }
]
    """

    json_string = input()
    bus_data = json.loads(json_string)

    # bus_data = json.loads(data_3)

    for data_dict in bus_data:
        for key, value in data_dict.items():
            if key == "bus_id":
                if not isinstance(value, int):
                    error_log["bus_id"] += 1
                    # print(f'bus_id: {value}')
            elif key == "stop_id":
                if not isinstance(value, int):
                    error_log["stop_id"] += 1
                    # print(f'stop_id: {value}')
            elif key == "stop_name":
                if not isinstance(value, str) or value == "" or not value[0].isupper() or (value.split()[-1] not in ["Road", "Avenue", "Boulevard", "Street"] or len(value.split()) < 2):
                    error_log["stop_name"] += 1
                    # print(f'stop_name: {value}')
            elif key == "next_stop":
                if not isinstance(value, int):
                    error_log["next_stop"] += 1
                    # print(f'next_stop: {value}')
            elif key == "stop_type":
                if value == "":
                    continue
                if not isinstance(value, str) or value not in ["S", "O", "F"] or len(value) > 1:
                    error_log["stop_type"] += 1
                    # print(f'stop_type: {value}')
            elif key == "a_time":
                if not isinstance(value, str) or str(value) == "" or not is_valid_time(value):
                    error_log["a_time"] += 1
                    # print(f'a_time: {value}')

    print(f'Format validation: {sum(error_log.values())} errors')
    print(f'stop_name: {error_log["stop_name"]}')
    print(f'stop_type: {error_log["stop_type"]}')
    print(f'a_time: {error_log["a_time"]}')

    # for k, v in error_log.items():
    #     print(f'{k}: {v}')


if __name__ == "__main__":
    main()
