import os
from subprocess import Popen, PIPE
from time import time


def java_build(name, source_path, java_file, class_output, class_path, add_flags=''):
    print('Build java project: ' + name)
    print('^^^Input path: ' + java_file)
    print('^^^Output path: ' + class_output)

    original = os.getcwd()
    os.chdir(name)

    start = time()

    source_opt = ' -sourcepath ' + source_path
    class_opt = ' -classpath ' + class_path if class_path != 'None' else ''
    output_opt = ' -d ' + class_output
    source_file = ' ' + java_file

    process = Popen('javac'+output_opt+class_opt+source_opt+source_file+add_flags, stdout=PIPE, stderr=PIPE)
    stdout, stderr = process.communicate()

    end = time()-start
    print(stdout)
    print(stderr)
    print('Time Taken: ' + str(end))

    os.chdir(original)

    # For summary of build
    return [stdout, stderr, end]


def java_lib(name, class_output, jar_content, prod_path, manifest='', add_flags=''):
    print('Build java library: ' + name)
    print('^^^Input path: ' + class_output)
    print('^^^Output path: ' + prod_path)

    original = os.getcwd()
    os.chdir(os.path.join(name, class_output))

    start = time()

    manifest_opt = ' --manifest ' + manifest if manifest != 'None' or manifest != '' else ''
    content_opt = ' ' + jar_content
    output_opt = ' --file "' + prod_path + '\\' + name + '.jar"'

    process = Popen('jar --verbose --create'+manifest_opt+output_opt+content_opt+add_flags, stdout=PIPE, stderr=PIPE)
    stdout, stderr = process.communicate()

    end = time() - start
    print(stdout)
    print(stderr)
    print('Time Taken: ' + str(end))

    os.chdir(original)

    # For summary of build
    return [stdout, stderr, end]