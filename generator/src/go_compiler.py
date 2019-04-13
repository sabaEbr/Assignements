import os
from subprocess import Popen, PIPE
from time import time


def go_build(project_path, prod_path, name, source_file, output_path, add_flags=''):
    """

    :param project_path:
    :param prod_path:
    :param name:
    :param source_file:
    :param output_path:
    :param add_flags:
    :return:
    """
    source_file = source_file.replace('$project_path', project_path)
    exe_file = os.path.join(output_path.replace('$prod_path', prod_path).replace('$project_path', project_path),
                               name + '.exe')

    print('\nCompiling Go project: {}'.format(name))
    print('^^^ Source package main: {}'.format(source_file))
    print('^^^ Output path: {}'.format(os.path.dirname(exe_file)))

    start = time()

    process = Popen('go build -ldflags="-s -w" -gcflags=-m -o {} {} {}'.format(exe_file, source_file, add_flags),
                    stdout=PIPE, stderr=PIPE)
    stdout, stderr = process.communicate()

    total_time = time() - start
    stdout_string = stdout.decode('utf-8')
    stderr_string = stdout.decode('utf-8')
    print(stdout_string)
    print(stderr_string)
    print('Time Taken: ' + str(total_time))

    # For summary of build
    return [stdout, stderr, total_time]