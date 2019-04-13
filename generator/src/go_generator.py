import os
import sys
from ruamel.yaml import YAML
from .java_compiler import java_build, java_lib
from .go_compiler import go_build


class GoGen:

    def __init__(self, program_path):
        # Read generation program
        yaml = YAML()
        with open(program_path, 'r') as program:
            self.program = yaml.load(program)
        self.project_path = self.program['project_path']
        self.prod_path = self.program['prod_path']

        self.switcher = {
            'java': self.java_work,
            'go': self.go_work
        }

    def __call__(self):
        """Initiates sequencing for program compilation

        :return:
        """
        if not os.path.isdir(self.prod_path):
            print('Creating production dir since it doesn\'t exist : ' + self.prod_path)
            os.makedirs(self.prod_path)

        os.chdir(self.project_path)

        # Call each generator depending on language
        # Todo: Store resume of generation for logging
        [self.switcher.get(lang, self.nothing)(self.program['sequencer'][lang]) for lang in self.program['sequencer']]

    # Return value structure for compiler functions
    rd = {'type': tuple, 'size': 3, 'fields': 'stdout, stderr, time'}

    def java_work(self, java_sequence) -> rd:
        """Send java project to the java build and lib func

        :param java_sequence:
        :return:
        """
        for project in java_sequence:
            for key in project.keys():
                exec(key + ' = \'' + str(project[key]) + '\'', globals())

            java_build(name, source_path, java_file, class_output, class_path, self.prod_path)
            java_lib(name, class_output, jar_content, self.prod_path, manifest)

    def go_work(self, go_sequence) -> rd:
        """Send go project to the go build func

        :param go_sequence:
        :return:
        """
        for project in go_sequence:
            for key in project.keys():
                exec(key + ' = \'' + str(project[key]) + '\'', globals())

            go_build(self.project_path, self.prod_path, name, source, output_path)

    def nothing(self):
        """Dummy func for sequences that are not defined

        :return:
        """
        pass


if __name__ == "__main__":
    # todo: Add argument parsing to code
    # todo: Update to correct logger practices
    # todo: Add tests to generators
    GoGen(sys.argv[1])()
