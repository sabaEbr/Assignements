import os
from subprocess import Popen, PIPE
from time import time


def java_build(name, source_path, java_file, class_output, class_path, prod_path, add_flags=''):
    """ Build java classes from java source code

    :param name:
    :param source_path:
    :param java_file:
    :param class_output:
    :param class_path:
    :param prod_path:
    :param add_flags:
    :return:
    """
    print('\nBuild java project: ' + name)
    print('^^^ Input path: ' + java_file)
    print('^^^ Output path: ' + class_output)

    original = os.getcwd()
    os.chdir(name)

    start = time()

    source_opt = ' -sourcepath ' + source_path
    class_opt = ' -classpath ' + class_path.replace('$prod_path', prod_path) if class_path != 'None' else ''
    output_opt = ' -d ' + class_output
    source_file = ' ' + java_file

    process = Popen('javac -verbose'+output_opt+class_opt+source_opt+source_file+add_flags, stdout=PIPE, stderr=PIPE)
    stdout, stderr = process.communicate()

    total_time = time()-start
    stdout_string = stdout.decode('utf-8')
    stderr_string = stdout.decode('utf-8')
    print(stdout_string)
    # print(stderr_string)
    print('Time Taken: ' + str(total_time))

    os.chdir(original)

    # For summary of build
    return [stdout, stderr, total_time]


def java_lib(name, class_output, jar_content, prod_path, manifest='', add_flags=''):
    """Create java library from compiled java programs

    :param name:
    :param class_output:
    :param jar_content:
    :param prod_path:
    :param manifest:
    :param add_flags:
    :return:
    """
    print('\nBuild java library: ' + name)
    print('^^^ Input path: ' + class_output)
    print('^^^ Output path: ' + prod_path)

    original = os.getcwd()
    os.chdir(os.path.join(name, class_output))

    start = time()

    manifest_opt = ' --manifest ' + manifest if manifest != 'None' or manifest != '' else ''
    content_opt = ' ' + jar_content
    output_opt = ' --file "' + prod_path + '\\' + name + '.jar"'

    process = Popen('jar --verbose --create'+manifest_opt+output_opt+content_opt+add_flags,
                    stdout=PIPE, stderr=PIPE)
    stdout, stderr = process.communicate()

    total_time = time() - start
    stdout_string = stdout.decode('utf-8')
    # stderr_string = stdout.decode('utf-8')
    print(stdout_string)
    # print(stderr_string)
    print('Time Taken: ' + str(total_time))

    os.chdir(original)

    # For summary of build
    return [stdout, stderr, total_time]