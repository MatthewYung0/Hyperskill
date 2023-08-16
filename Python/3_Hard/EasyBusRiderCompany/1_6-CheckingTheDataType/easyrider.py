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

    json_string = input()
    bus_data = json.loads(json_string)

    for data_dict in bus_data:
        for key, value in data_dict.items():
            if key == "bus_id":
                if not isinstance(value, int):
                    error_log["bus_id"] += 1
            elif key == "stop_id":
                if not isinstance(value, int):
                    error_log["stop_id"] += 1
            elif key == "stop_name":
                if not isinstance(value, str) or \
                        value == "" or \
                        (not value[0].isupper() and
                         value.split()[-1] not in ["Road", "Avenue", "Boulevard", "Street"]):
                    error_log["stop_name"] += 1
            elif key == "next_stop":
                if not isinstance(value, int):
                    error_log["next_stop"] += 1
            elif key == "stop_type":
                if not isinstance(value, str) and value not in ["S", "O", "F"] or len(value) > 1:
                    error_log["stop_type"] += 1
            elif key == "a_time":
                if not isinstance(value, str) or str(value) == "" or not is_valid_time(value):
                    error_log["a_time"] += 1

    print(f'Type and required field validation: {sum(error_log.values())} errors')
    for k, v in error_log.items():
        print(f'{k}: {v}')


if __name__ == "__main__":
    main()
