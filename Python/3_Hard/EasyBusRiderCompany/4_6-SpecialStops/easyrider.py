import json
import re

def is_valid_time(time_string):
    pattern = r'^([01]\d|2[0-3]):([0-5]\d)$'
    return re.match(pattern, time_string) is not None


def has_start_stop(data_, bus_lines_):
    for bus_line in bus_lines_:
        has_start = False
        has_stop = False
        for item in data_:
            if item["bus_id"] == bus_line:
                if item["stop_type"] == "S":
                    if has_start is False:
                        has_start = True
                    else:
                        return bus_line, False
                elif item["stop_type"] == "F":
                    if has_stop is False:
                        has_stop = True
                    else:
                        return bus_line, False
        if (has_start is False) or (has_stop is False):
            return bus_line, False
    return 1, True


def get_start_stops(data_):
    start_stops = []
    for item in data_:
        if item["stop_type"] == "S":
            start_stops.append(item["stop_name"])
    return sorted(list(dict.fromkeys(start_stops)))


def get_final_stops(data_):
    final_stops = []
    for item in data_:
        if item["stop_type"] == "F":
            final_stops.append(item["stop_name"])
    return sorted(list(dict.fromkeys(final_stops)))


def get_transfer_stops(data_):
    streets = {}
    transfer_stops = []

    for item in data_:
        value = item.get("stop_name")
        if value:
            if value in streets:
                streets[value] += 1
            else:
                streets[value] = 1

    for key, value in streets.items():
        if value > 1:
            transfer_stops.append(key)
    return sorted(list(dict.fromkeys(transfer_stops)))


def get_errors_and_bus_lines(data_):
    bus_lines = {}
    error_log = {
        "bus_id": 0,
        "stop_id": 0,
        "stop_name": 0,
        "next_stop": 0,
        "stop_type": 0,
        "a_time": 0
    }

    for data_dict in data_:
        for key, value in data_dict.items():
            if key == "bus_id":
                if not isinstance(value, int):
                    error_log["bus_id"] += 1
                else:
                    if value in bus_lines:
                        bus_lines[value] += 1
                    else:
                        bus_lines[value] = 1
            elif key == "stop_id":
                if not isinstance(value, int):
                    error_log["stop_id"] += 1
            elif key == "stop_name":
                if not isinstance(value, str) or value == "" or not value[0].isupper() or (
                        value.split()[-1] not in ["Road", "Avenue", "Boulevard", "Street"] or len(value.split()) < 2):
                    error_log["stop_name"] += 1
            elif key == "next_stop":
                if not isinstance(value, int):
                    error_log["next_stop"] += 1
            elif key == "stop_type":
                if value == "":
                    continue
                if not isinstance(value, str) or value not in ["S", "O", "F"] or len(value) > 1:
                    error_log["stop_type"] += 1
            elif key == "a_time":
                if not isinstance(value, str) or str(value) == "" or not is_valid_time(value):
                    error_log["a_time"] += 1
    return error_log, bus_lines


def main():
    json_string = input()
    bus_data = json.loads(json_string)
    # bus_data = json.loads(data_t2)

    error_log, bus_lines = get_errors_and_bus_lines(bus_data)
    bus_line, start_stop = has_start_stop(bus_data, bus_lines)

    # print(f'Format validation: {sum(error_log.values())} errors')
    # print(f'stop_name: {error_log["stop_name"]}')
    # print(f'stop_type: {error_log["stop_type"]}')
    # print(f'a_time: {error_log["a_time"]}')

    if start_stop is False:
        print("There is no start or end stop for the line: " + str(bus_line))
    else:
        start_stops = get_start_stops(bus_data)
        transfer_stops = get_transfer_stops(bus_data)
        final_stops = get_final_stops(bus_data)

        print("Start stops: {} {}".format(str(len(start_stops)), start_stops))
        print("Transfer stops: {} {}".format(str(len(transfer_stops)), transfer_stops))
        print("Finish stops: {} {}".format(str(len(final_stops)), final_stops))

    # print("Line names and number of stops:")
    # print(f"bus_id: 128, stops: {bus_lines[128]}")
    # print(f"bus_id: 256, stops: {bus_lines[256]}")
    # print(f"bus_id: 512, stops: {bus_lines[512]}")

    # for k, v in error_log.items():
    #     print(f'{k}: {v}')


if __name__ == "__main__":
    main()
