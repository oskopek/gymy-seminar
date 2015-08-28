<?xml version="1.0" encoding="UTF-8"?>
<plannerBenchmark>
  <benchmarkDirectory>local/data/seminar</benchmarkDirectory>
  <parallelBenchmarkCount>AUTO</parallelBenchmarkCount>
  <warmUpSecondsSpentLimit>30</warmUpSecondsSpentLimit>

  <inheritedSolverBenchmark>
    <problemBenchmarks>
      <xStreamAnnotatedClass>sk.gymy.seminar.domain.Groups</xStreamAnnotatedClass>
      <!--<inputSolutionFile>data/seminarAssignment/unsolved/G3Ch2St20Tea6Sem15-seminarAssignment.xml</inputSolutionFile>-->
      <inputSolutionFile>data/seminar/unsolved/G3Ch2St200Tea60Sem150-seminar.xml</inputSolutionFile>
      <inputSolutionFile>data/seminar/unsolved/G3Ch2St2000Tea600Sem1500-seminar.xml</inputSolutionFile>
      <inputSolutionFile>data/seminar/unsolved/G3Ch5St2000Tea60Sem125-seminar.xml</inputSolutionFile>
      <inputSolutionFile>data/seminar/unsolved/G5Ch3St2000Tea60Sem125-seminar.xml</inputSolutionFile>
      <!--<inputSolutionFile>data/seminarAssignment/unsolved/gymy2014-2.xml</inputSolutionFile>-->
      <!--<inputSolutionFile>data/seminarAssignment/unsolved/gymy2014-4.xml</inputSolutionFile>-->
      <!--<inputSolutionFile>data/seminarAssignment/unsolved/simple5.xml</inputSolutionFile>-->
      <!--<inputSolutionFile>data/seminarAssignment/unsolved/unsolvable5.xml</inputSolutionFile>-->
      <writeOutputSolutionEnabled>true</writeOutputSolutionEnabled>
      <problemStatisticType>BEST_SCORE</problemStatisticType>
    </problemBenchmarks>
    <solver>
      <solutionClass>sk.gymy.seminar.domain.Groups</solutionClass>
      <entityClass>sk.gymy.seminar.domain.Seminar</entityClass>
      <scoreDirectorFactory>
        <scoreDefinitionType>HARD_SOFT</scoreDefinitionType>
        <scoreDrl>sk/gymy/seminar/solver/seminarScoreRules.drl</scoreDrl>
        <initializingScoreTrend>ONLY_DOWN</initializingScoreTrend>
      </scoreDirectorFactory>
      <termination>
        <terminationCompositionStyle>OR</terminationCompositionStyle>
        <bestScoreLimit>0hard/0soft</bestScoreLimit>
        <minutesSpentLimit>5</minutesSpentLimit>
      </termination>
      <constructionHeuristic>
        <constructionHeuristicType>FIRST_FIT</constructionHeuristicType>
      </constructionHeuristic>
    </solver>
  </inheritedSolverBenchmark>

<#list [150, 200, 250] as hardTemp>
<#list [4, 8, 12, 16] as acceptedCountLimit>
  <solverBenchmark>
    <name>SA hTemp-${hardTemp} ACL-${acceptedCountLimit}</name>
    <solver>
      <localSearch>
        <unionMoveSelector>
          <changeMoveSelector/>
          <swapMoveSelector/>
          <pillarChangeMoveSelector/>
          <pillarSwapMoveSelector/>
        </unionMoveSelector>
        <acceptor>
          <simulatedAnnealingStartingTemperature>${hardTemp}hard/400soft</simulatedAnnealingStartingTemperature>
        </acceptor>
        <forager>
          <acceptedCountLimit>${acceptedCountLimit}</acceptedCountLimit>
        </forager>
      </localSearch>
    </solver>
  </solverBenchmark>
</#list>
</#list>
</plannerBenchmark>
