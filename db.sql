select *
into #POND_BOND
from (
         select pb.*
         from EDW_Staging.OSS.POND_BOND pb
                  inner join (select max(transaction_number)         transaction_number,
                                     max(POND_VERSION_NUMBER)        POND_VERSION_NUMBER,
                                     BOND_NUMBER,
                                     CAST(INCEPTION_DATE as date) AS INCEPTION_DATE
                              from EDW_Staging.OSS.POND_BOND WITH (NOLOCK)
                              where Bond_status_code in ( 'ACTIVE', 'VOIDS', 'VOIDED')
                              group by BOND_NUMBER, CAST (INCEPTION_DATE as date)) pbu
                             on pb.BOND_NUMBER = pbu.BOND_NUMBER and
                                cast(pb.INCEPTION_DATE as date) =
                                cast(pbu.INCEPTION_DATE as date) and
                                pb.transaction_number = pbu.transaction_number and
                                pb.POND_VERSION_NUMBER = pbu.POND_VERSION_NUMBER) a

drop table #temp
select distinct SrceSysIntRef_CoverageId
from #temp
where CovgTypeMapId = 1
select count(*), CovgTypeMapId
from #temp
group by CovgTypeMapId
select *
from #temp
where CovgId = 1

select *
from Integration.Pol_Coverage
where srcesysid = 21
  and CovgTypeMapId = 1
select *
into #temp
from (
         select distinct RiskId = ISNULL(coverage.RiskId, 1),
                         InsdIntrId = ISNULL(coverage.InsdIntrId, 1),
                         CovgId = ISNULL(coverage.CovgId, 1),
                         CovgTypeMapId = ISNULL(MapCvg.CovgTypeId, 1),
                         EfftFromDate = CAST(b.INCEPTION_DATE AS DATE),
                         EfftToDate = CAST(b.EXPIRATION_DATE AS DATE),
                         OrigCurLimitMapId = mapcurrcode.CurCodeMapId,
                         SettCurLimitMapId = mapcurrcode.CurCodeMapId,
                         EntBaseCurCodeId = mapcurrcode.CurCodeMapId,
                         SrceSysIntRef_CoverageId

         from (
                  SELECT DISTINCT a.BOND_NUMBER + '~' + FORMAT(a.INCEPTION_DATE, 'yyyyMMdd') + '~' +
                                  a.CoverageType                                             as SrceSysIntRef_CoverageId,
                                  a.BOND_NUMBER + '~' + FORMAT(a.INCEPTION_DATE, 'yyyyMMdd') as SrceSysIntRef_LnkCoverage,
                                  EXPIRATION_DATE,
                                  INCEPTION_DATE,
                                  BOND_NUMBER,
                                  CAN_CAN_BUS,
                                  RECORD_DATE,
                                  a.DelInd
                                  --a.BOND_TYPE,a.Transaction_type_code,a.TRANSACTION_TYPE_SHORT_DESC
                  FROM (
                           SELECT --A.BOND_ID,

                                  BOND_NUMBER,
                                  A.BOND_TYPE,
                                  --BOND_STATUS_CODE,
                                  C.TRANSACTION_TYPE_CODE,
                                  C.TRANSACTION_TYPE_SHORT_DESC,
                                  --SHORT_NAME,
                                  INCEPTION_DATE,
                                  B.A_O_P_COVERAGE_TYPE CoverageType,
                                  CASE
                                      WHEN ISNULL(A_O_P_DEBIT_PREMIUM - A_O_P_CREDIT_PREMIUM,
                                                  0) >
                                           0
                                          OR ISNULL(A_O_P_COVERAGE_LIMIT, 0) > 0
                                          OR ISNULL(A_O_P_F_DEDUCTIBLE, 0) > 0
                                          THEN
                                          'A_O_P_COVERAGE'
                                      END
                                      AS                COVERAGE,
                                  a.EXPIRATION_DATE,
                                  a.BUSINESS_CLASS_DESC_ID,
                                  a.CAN_CAN_BUS,
                                  a.RECORD_DATE,
                                  a.DelInd


                                  ---- A_O_P_DEBIT_PREMIUM - A_O_P_CREDIT_PREMIUM
                                  ------AS PREMIUM,
                                  --A_O_P_F_DEDUCTIBLE
                                  --  --AS DEDUCIBLE,
                                  --A_O_P_COVERAGE_LIMIT
                                  --  --AS LIMIT
                           FROM #POND_BOND A WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND
                               A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE

                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           ----AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (A_O_P_DEBIT_PREMIUM - A_O_P_CREDIT_PREMIUM
                               , 0)
                               > 0
                              OR ISNULL (A_O_P_COVERAGE_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (A_O_P_F_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'B' CoverageType,
                               CASE
                               WHEN ISNULL (B_DEBIT_PREMIUM - B_CREDIT_PREMIUM, 0) > 0
                               OR ISNULL (B_COVERAGE_LIMIT, 0) > 0
                               OR ISNULL (B_DEDUCTIBLE, 0) > 0
                               THEN
                               'B_COVERAGE'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --B_DEBIT_PREMIUM - B_CREDIT_PREMIUM
                               ----AS PREMIUM,
                               --B_DEDUCTIBLE
                               --  --AS DEDUCIBLE,
                               --B_COVERAGE_LIMIT
                               --  --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (B_DEBIT_PREMIUM - B_CREDIT_PREMIUM
                               , 0)
                               > 0
                              OR ISNULL (B_COVERAGE_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (B_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               -- B.C_Q_COVERAGE_TYPE CoverageType,
                               ISNULL(B.C_Q_COVERAGE_TYPE, 'C') CoverageType,
                               CASE
                               WHEN ISNULL (C_Q_DEBIT_PREMIUM - C_Q_CREDIT_PREMIUM, 0) > 0
                               OR ISNULL (C_Q_COVERAGE_LIMIT, 0) > 0
                               OR ISNULL (C_Q_DEDUCTIBLE, 0) > 0
                               THEN
                               'C_Q_COVERAGE'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --C_Q_DEBIT_PREMIUM - C_Q_CREDIT_PREMIUM
                               --   --AS PREMIUM,
                               --C_Q_DEDUCTIBLE
                               --  --AS DEDUCIBLE,
                               --C_Q_COVERAGE_LIMIT
                               --  --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (C_Q_DEBIT_PREMIUM - C_Q_CREDIT_PREMIUM
                               , 0)
                               > 0
                              OR ISNULL (C_Q_COVERAGE_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (C_Q_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'F' CoverageType,
                               CASE
                               WHEN ISNULL (F_DEBIT_PREMIUM - F_CREDIT_PREMIUM, 0) > 0
                               OR ISNULL (F_COVERAGE_LIMIT, 0) > 0
                               OR ISNULL (F_DEDUCTIBLE, 0) > 0
                               THEN
                               'F_COVERAGE'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --F_DEBIT_PREMIUM - F_CREDIT_PREMIUM
                               --  --AS PREMIUM,
                               --F_DEDUCTIBLE
                               --  --AS DEDUCIBLE,
                               --F_COVERAGE_LIMIT
                               --  --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (F_DEBIT_PREMIUM - F_CREDIT_PREMIUM
                               , 0)
                               > 0
                              OR ISNULL (F_COVERAGE_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (F_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'AGENTS_COVERAGE' CoverageType,
                               CASE
                               WHEN ISNULL (AGENTS_DEBIT_PREMIUM - AGENTS_CREDIT_PREMIUM,
                               0) > 0
                               OR ISNULL (AGENTS_COVERAGE_LIMIT, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               THEN
                               'AGENTS_COVERAGE'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --AGENTS_DEBIT_PREMIUM - AGENTS_CREDIT_PREMIUM
                               ----AS PREMIUM,
                               -- NULL
                               ----AS DEDUCIBLE,
                               --AGENTS_COVERAGE_LIMIT
                               ----AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (AGENTS_DEBIT_PREMIUM - AGENTS_CREDIT_PREMIUM
                               , 0)
                               > 0
                              OR ISNULL (AGENTS_COVERAGE_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'EXCESS_O_OVER_P' CoverageType,
                               CASE
                               WHEN ISNULL (
                               EXCESS_O_OVER_P_DEBIT_PREMIUM
                               - EXCESS_O_OVER_P_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (EXCESS_O_OVER_P_LIMIT, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               THEN
                               'EXCESS_O_OVER_P'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --EXCESS_O_OVER_P_DEBIT_PREMIUM - EXCESS_O_OVER_P_CREDIT_PREMIUM
                               --  --AS PREMIUM,
                               --NULL
                               --  --AS DEDUCIBLE,
                               --EXCESS_O_OVER_P_LIMIT
                               --  --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               EXCESS_O_OVER_P_DEBIT_PREMIUM
                               - EXCESS_O_OVER_P_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (EXCESS_O_OVER_P_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'SPECIFIC_EXCESS_HIGH' CoverageType,
                               CASE
                               WHEN ISNULL (
                               SPECIFIC_EXCESS_DEBIT_PREMIUM
                               - SPECIFIC_EXCESS_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (SPECIFIC_EXCESS_HIGH_LIMIT, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               THEN
                               'SPECIFIC_EXCESS_HIGH'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --SPECIFIC_EXCESS_DEBIT_PREMIUM - SPECIFIC_EXCESS_CREDIT_PREMIUM
                               --  --AS PREMIUM,
                               --NULL
                               --  --AS DEDUCIBLE,
                               --SPECIFIC_EXCESS_HIGH_LIMIT
                               --  --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               SPECIFIC_EXCESS_DEBIT_PREMIUM
                               - SPECIFIC_EXCESS_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (SPECIFIC_EXCESS_HIGH_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'PEAK_SEASON_COVERAGE' CoverageType,
                               CASE
                               WHEN ISNULL (
                               PEAK_SEASON_DEBIT_PREMIUM - PEAK_SEASON_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (NULL, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               THEN
                               'PEAK_SEASON_COVERAGE'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --PEAK_SEASON_DEBIT_PREMIUM - PEAK_SEASON_CREDIT_PREMIUM
                               --  --AS PREMIUM,
                               --NULL
                               --  --AS DEDUCIBLE,
                               --NULL
                               --  --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               PEAK_SEASON_DEBIT_PREMIUM - PEAK_SEASON_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (NULL
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'FAITHFUL_PERFORMANCE' CoverageType,
                               CASE
                               WHEN ISNULL (NULL, 0) > 0
                               OR ISNULL (FAITHFUL_PERFORMANCE_LIMIT, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               THEN
                               'FAITHFUL_PERFORMANCE'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --NULL
                               --  --AS PREMIUM,
                               --NULL
                               --  --AS DEDUCIBLE,
                               --FAITHFUL_PERFORMANCE_LIMIT
                               --  --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (NULL
                               , 0)
                               > 0
                              OR ISNULL (FAITHFUL_PERFORMANCE_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'WAIVED_PREMIUM' CoverageType,
                               CASE
                               WHEN ISNULL (WAIVED_PREMIUM, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               THEN
                               'WAIVED_PREMIUM'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --WAIVED_PREMIUM
                               --AS PREMIUM,
                               --NULL
                               --AS DEDUCIBLE,
                               --NULL
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (WAIVED_PREMIUM
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'SURCHARGE' CoverageType,
                               CASE
                               WHEN ISNULL (SURCHARGE, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               OR ISNULL (NULL, 0) > 0
                               THEN
                               'SURCHARGE'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --SURCHARGE
                               --AS PREMIUM,
                               --NULL
                               --AS DEDUCIBLE,
                               --NULL
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (SURCHARGE
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0
                              OR ISNULL (NULL
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'THRDPRTY' CoverageType,
                               CASE
                               WHEN ISNULL (
                               THRDPRTY_DEBIT_PREMIUM
                               - THRDPRTY_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (THRDPRTY_LIMIT, 0) > 0
                               OR ISNULL (THRDPRTY_DEDUCTIBLE, 0) > 0
                               THEN
                               'THRDPRTY'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --THRDPRTY_DEBIT_PREMIUM - THRDPRTY_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --THRDPRTY_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --THRDPRTY_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (THRDPRTY_DEBIT_PREMIUM - THRDPRTY_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (THRDPRTY_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (THRDPRTY_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'COMPFRD' CoverageType,
                               CASE
                               WHEN ISNULL (
                               COMPFRD_DEBIT_PREMIUM - COMPFRD_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (COMPFRD_LIMIT, 0) > 0
                               OR ISNULL (COMPFRD_DEDUCTIBLE, 0) > 0
                               THEN
                               'COMPFRD'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --COMPFRD_DEBIT_PREMIUM - COMPFRD_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --COMPFRD_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --COMPFRD_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (COMPFRD_DEBIT_PREMIUM - COMPFRD_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (COMPFRD_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (COMPFRD_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'CNTRCRNCY' CoverageType,
                               CASE
                               WHEN ISNULL (
                               CNTRCRNCY_DEBIT_PREMIUM
                               + CNTRCRNCY_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (CNTRCRNCY_LIMIT, 0) > 0
                               OR ISNULL (CNTRCRNCY_DEDUCTIBLE, 0) > 0
                               THEN
                               'CNTRCRNCY'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --CNTRCRNCY_DEBIT_PREMIUM + CNTRCRNCY_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --CNTRCRNCY_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --CNTRCRNCY_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               CNTRCRNCY_DEBIT_PREMIUM + CNTRCRNCY_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (CNTRCRNCY_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (CNTRCRNCY_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'FUNDSXFER' CoverageType,
                               CASE
                               WHEN ISNULL (
                               FUNDSXFER_DEBIT_PREMIUM
                               - FUNDSXFER_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (FUNDSXFER_LIMIT, 0) > 0
                               OR ISNULL (FUNDSXFER_DEDUCTIBLE, 0) > 0
                               THEN
                               'FUNDSXFER'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --FUNDSXFER_DEBIT_PREMIUM - FUNDSXFER_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --FUNDSXFER_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --FUNDSXFER_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               FUNDSXFER_DEBIT_PREMIUM - FUNDSXFER_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (FUNDSXFER_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (FUNDSXFER_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'CC_FORGERY' CoverageType,
                               CASE
                               WHEN ISNULL (
                               CC_FORGERY_DEBIT_PREMIUM
                               - CC_FORGERY_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (CC_FORGERY_LIMIT, 0) > 0
                               OR ISNULL (CC_FORGERY_DEDUCTIBLE, 0) > 0
                               THEN
                               'CC_FORGERY'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --CC_FORGERY_DEBIT_PREMIUM - CC_FORGERY_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --CC_FORGERY_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --CC_FORGERY_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               CC_FORGERY_DEBIT_PREMIUM
                               - CC_FORGERY_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (CC_FORGERY_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (CC_FORGERY_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'EMP_THEFT_EMP' CoverageType,
                               CASE
                               WHEN ISNULL (
                               EMP_THEFT_EMP_DEBIT_PREMIUM
                               - EMP_THEFT_EMP_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (EMP_THEFT_EMP_LIMIT, 0) > 0
                               OR ISNULL (EMP_THEFT_EMP_DEDUCTIBLE, 0) > 0
                               THEN
                               'EMP_THEFT_EMP'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --EMP_THEFT_EMP_DEBIT_PREMIUM - EMP_THEFT_EMP_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --EMP_THEFT_EMP_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --EMP_THEFT_EMP_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               EMP_THEFT_EMP_DEBIT_PREMIUM
                               - EMP_THEFT_EMP_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (EMP_THEFT_EMP_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (EMP_THEFT_EMP_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'MOCF_CURRENCY' CoverageType,
                               CASE
                               WHEN ISNULL (
                               MOCF_CURRENCY_DEBIT_PREMIUM
                               - MOCF_CURRENCY_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (MOCF_CURRENCY_LIMIT, 0) > 0
                               OR ISNULL (MOCF_CURRENCY_DEDUCTIBLE, 0) > 0
                               THEN
                               'MOCF_CURRENCY'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --MOCF_CURRENCY_DEBIT_PREMIUM - MOCF_CURRENCY_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --MOCF_CURRENCY_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --MOCF_CURRENCY_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               MOCF_CURRENCY_DEBIT_PREMIUM
                               - MOCF_CURRENCY_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (MOCF_CURRENCY_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (MOCF_CURRENCY_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'EXPENSE_REIM' CoverageType,
                               CASE
                               WHEN ISNULL (
                               EXPENSE_REIM_DEBIT_PREMIUM
                               - EXPENSE_REIM_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (EXPENSE_REIM_LIMIT, 0) > 0
                               OR ISNULL (EXPENSE_REIM_DEDUCTIBLE, 0) > 0
                               THEN
                               'EXPENSE_REIM'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --EXPENSE_REIM_DEBIT_PREMIUM - EXPENSE_REIM_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --EXPENSE_REIM_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --EXPENSE_REIM_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               EXPENSE_REIM_DEBIT_PREMIUM
                               - EXPENSE_REIM_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (EXPENSE_REIM_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (EXPENSE_REIM_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'COMP_SYST_REST' CoverageType,
                               CASE
                               WHEN ISNULL (
                               COMP_SYST_REST_DEBIT_PREMIUM
                               - COMP_SYST_REST_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (COMP_SYST_REST_LIMIT, 0) > 0
                               OR ISNULL (COMP_SYST_REST_DEDUCTIBLE, 0) > 0
                               THEN
                               'COMP_SYST_REST'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --COMP_SYST_REST_DEBIT_PREMIUM - COMP_SYST_REST_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --COMP_SYST_REST_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --COMP_SYST_REST_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               COMP_SYST_REST_DEBIT_PREMIUM
                               - COMP_SYST_REST_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (COMP_SYST_REST_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (COMP_SYST_REST_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'CF_TRANS_FRAUD' CoverageType,
                               CASE
                               WHEN ISNULL (
                               CF_TRANS_FRAUD_DEBIT_PREMIUM
                               - CF_TRANS_FRAUD_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (CF_TRANS_FRAUD_LIMIT, 0) > 0
                               OR ISNULL (CF_TRANS_FRAUD_DEDUCTIBLE, 0) > 0
                               THEN
                               'CF_TRANS_FRAUD'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --CF_TRANS_FRAUD_DEBIT_PREMIUM - CF_TRANS_FRAUD_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --CF_TRANS_FRAUD_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --CF_TRANS_FRAUD_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               CF_TRANS_FRAUD_DEBIT_PREMIUM
                               - CF_TRANS_FRAUD_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (CF_TRANS_FRAUD_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (CF_TRANS_FRAUD_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'DEP_FORG_ALT' CoverageType,
                               CASE
                               WHEN ISNULL (
                               DEP_FORG_ALT_DEBIT_PREMIUM
                               - DEP_FORG_ALT_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (DEP_FORG_ALT_LIMIT, 0) > 0
                               OR ISNULL (DEP_FORG_ALT_DEDUCTIBLE, 0) > 0
                               THEN
                               'DEP_FORG_ALT'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --DEP_FORG_ALT_DEBIT_PREMIUM - DEP_FORG_ALT_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --DEP_FORG_ALT_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --DEP_FORG_ALT_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               DEP_FORG_ALT_DEBIT_PREMIUM
                               - DEP_FORG_ALT_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (DEP_FORG_ALT_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (DEP_FORG_ALT_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'INSIDE_PREM' CoverageType,
                               CASE
                               WHEN ISNULL (
                               INSIDE_PREM_DEBIT_PREMIUM
                               - INSIDE_PREM_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (INSIDE_PREM_LIMIT, 0) > 0
                               OR ISNULL (INSIDE_PREM_DEDUCTIBLE, 0) > 0
                               THEN
                               'INSIDE_PREM'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --INSIDE_PREM_DEBIT_PREMIUM - INSIDE_PREM_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --INSIDE_PREM_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --INSIDE_PREM_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               INSIDE_PREM_DEBIT_PREMIUM
                               - INSIDE_PREM_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (INSIDE_PREM_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (INSIDE_PREM_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'INV_EXPENSES' CoverageType,
                               CASE
                               WHEN ISNULL (
                               INV_EXPENSES_DEBIT_PREMIUM
                               - INV_EXPENSES_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (INV_EXPENSES_LIMIT, 0) > 0
                               OR ISNULL (INV_EXPENSES_DEDUCTIBLE, 0) > 0
                               THEN
                               'INV_EXPENSES'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --INV_EXPENSES_DEBIT_PREMIUM - INV_EXPENSES_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --INV_EXPENSES_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --INV_EXPENSES_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               INV_EXPENSES_DEBIT_PREMIUM
                               - INV_EXPENSES_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (INV_EXPENSES_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (INV_EXPENSES_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'EMP_THEFT' CoverageType,
                               CASE
                               WHEN ISNULL (
                               EMP_THEFT_DEBIT_PREMIUM
                               - EMP_THEFT_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (EMP_THEFT_LIMIT, 0) > 0
                               OR ISNULL (EMP_THEFT_DEDUCTIBLE, 0) > 0
                               THEN
                               'EMP_THEFT'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --EMP_THEFT_DEBIT_PREMIUM - EMP_THEFT_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --EMP_THEFT_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --EMP_THEFT_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               EMP_THEFT_DEBIT_PREMIUM - EMP_THEFT_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (EMP_THEFT_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (EMP_THEFT_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'EMP_THEFT_CP' CoverageType,
                               CASE
                               WHEN ISNULL (
                               EMP_THEFT_CP_DEBIT_PREMIUM
                               - EMP_THEFT_CP_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (EMP_THEFT_CP_LIMIT, 0) > 0
                               OR ISNULL (EMP_THEFT_CP_DEDUCTIBLE, 0) > 0
                               THEN
                               'EMP_THEFT_CP'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --EMP_THEFT_CP_DEBIT_PREMIUM - EMP_THEFT_CP_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --EMP_THEFT_CP_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --EMP_THEFT_CP_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               EMP_THEFT_CP_DEBIT_PREMIUM
                               - EMP_THEFT_CP_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (EMP_THEFT_CP_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (EMP_THEFT_CP_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'OUTSIDE_PREM' CoverageType,
                               CASE
                               WHEN ISNULL (
                               OUTSIDE_PREM_DEBIT_PREMIUM
                               - OUTSIDE_PREM_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (OUTSIDE_PREM_LIMIT, 0) > 0
                               OR ISNULL (OUTSIDE_PREM_DEDUCTIBLE, 0) > 0
                               THEN
                               'OUTSIDE_PREM'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --OUTSIDE_PREM_DEBIT_PREMIUM - OUTSIDE_PREM_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --OUTSIDE_PREM_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --OUTSIDE_PREM_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               OUTSIDE_PREM_DEBIT_PREMIUM
                               - OUTSIDE_PREM_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (OUTSIDE_PREM_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (OUTSIDE_PREM_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'DECPTNFRD' CoverageType,
                               CASE
                               WHEN ISNULL (
                               DECPTNFRD_DEBIT_PREMIUM
                               - DECPTNFRD_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (DECPTNFRD_LIMIT, 0) > 0
                               OR ISNULL (DECPTNFRD_DEDUCTIBLE, 0) > 0
                               THEN
                               'DECPTNFRD'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --DECPTNFRD_DEBIT_PREMIUM - DECPTNFRD_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --DECPTNFRD_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --DECPTNFRD_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               DECPTNFRD_DEBIT_PREMIUM - DECPTNFRD_CREDIT_PREMIUM
                               , 0)
                               >
                               0
                              OR ISNULL (DECPTNFRD_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (DECPTNFRD_DEDUCTIBLE
                               , 0)
                               > 0)
                           UNION
                           SELECT --A.BOND_ID,
                               BOND_NUMBER, A.BOND_TYPE,
                               --BOND_STATUS_CODE,
                               C.TRANSACTION_TYPE_CODE, C.TRANSACTION_TYPE_SHORT_DESC,
                               --SHORT_NAME,
                               INCEPTION_DATE,
                               'VRTLCRNCY' CoverageType,
                               CASE
                               WHEN ISNULL (
                               VRTLCRNCY_DEBIT_PREMIUM
                               - VRTLCRNCY_CREDIT_PREMIUM,
                               0) >
                               0
                               OR ISNULL (VRTLCRNCY_LIMIT, 0) > 0
                               OR ISNULL (VRTLCRNCY_DEDUCTIBLE, 0) > 0
                               THEN
                               'VRTLCRNCY'
                               END
                               AS COVERAGE, a.EXPIRATION_DATE,
                               a.BUSINESS_CLASS_DESC_ID, a.CAN_CAN_BUS, a.RECORD_DATE, a.DelInd
                               --VRTLCRNCY_DEBIT_PREMIUM - VRTLCRNCY_CREDIT_PREMIUM
                               --AS PREMIUM,
                               --VRTLCRNCY_DEDUCTIBLE
                               --AS DEDUCIBLE,
                               --VRTLCRNCY_LIMIT
                               --AS LIMIT
                           FROM #POND_BOND A
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_BOND_FIDELITY_COMMERCIAL B
                           WITH (NOLOCK), EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE C
                           WITH (NOLOCK)--, EDW_STAGING.OSS.POND_RATE_PLAN C
                           WHERE A.BOND_ID = B.BOND_ID
                             AND A.TRANSACTION_TYPE_CODE= C.TRANSACTION_TYPE_CODE
                           --AND A.RATE_PLAN_ID = C.RATE_PLAN_ID
                           --AND YEAR(A.RECORD_DATE) >= 2005
                           --AND BOND_STATUS_CODE NOT IN (  'PENDING','CONVERT')
                             AND Bond_status_code in ( 'ACTIVE'
                               , 'VOIDS'
                               , 'VOIDED')
                             AND ( ISNULL (
                               VRTLCRNCY_DEBIT_PREMIUM - VRTLCRNCY_CREDIT_PREMIUM
                               , 0)
                               > 0
                              OR ISNULL (VRTLCRNCY_LIMIT
                               , 0)
                               > 0
                              OR ISNULL (VRTLCRNCY_DEDUCTIBLE
                               , 0)
                               > 0)
                       ) a
                  where A.TRANSACTION_TYPE_SHORT_DESC in ('NEW', 'RENEWAL', 'CANCELLATION')
                    AND A.BOND_TYPE IN ('F', 'G')

                  UNION ALL

                  SELECT DISTINCT B.BOND_NUMBER + '~' + FORMAT(INCEPTION_DATE, 'yyyyMMdd')-- +'~'+ convert(varchar(100),BUSINESS_CLASS_DESC_ID )
                                                                                           AS CoverageId,
                                  B.BOND_NUMBER + '~' + FORMAT(INCEPTION_DATE, 'yyyyMMdd') AS Srcesysintref,
                                  B.EXPIRATION_DATE,
                                  B.INCEPTION_DATE,
                                  B.BOND_NUMBER,
                                  B.CAN_CAN_BUS,
                                  B.RECORD_DATE,
                                  b.DelInd
                  FROM #POND_BOND B WITH (NOLOCK)
 INNER JOIN EDW_STAGING.OSS.POND_VAL_TRANSACTION_TYPE T
                  WITH (NOLOCK)
                  ON T.TRANSACTION_TYPE_CODE=B.TRANSACTION_TYPE_CODE
                  WHERE --B.BOND_STATUS_CODE NOT IN ('PENDING','CONVERT')  AND
                      T.TRANSACTION_TYPE_SHORT_DESC in ('NEW'
                      , 'RENEWAL'
                      , 'CANCELLATION')
                    AND B.BOND_STATUS_CODE in ( 'ACTIVE'
                      , 'VOIDS'
                      , 'VOIDED')
                    AND B.BOND_TYPE NOT IN ('F'
                      , 'G')
              ) as b
                  left JOIN EDW_STAGING.OSS.POND_BOND_TRANSACTION T WITH (NOLOCK)
         ON B.BOND_NUMBER = T.BOND_NUMBER
             left join EDW_STAGING.OSS.POND_BOND_COVERAGE C
         WITH (NOLOCK)
         ON C.BOND_TRAN_ID=T.BOND_TRAN_ID
             left join EDW_Integration.Integration.Lnk_RiskId risk
         WITH (NOLOCK)
         ON b.SrceSysIntRef_LnkCoverage = risk.PolSrceSysIntRef and
             risk.PolSrceSysId = 21
             left join EDW_Integration.Integration.Lnk_CoverageId coverage
         WITH (NOLOCK)
         ON b.SrceSysIntRef_CoverageId = coverage.SrceSysIntRef and risk.RISKID = coverage.RISKID and
             coverage.SrceSysId = 21
             left join EDW_Integration.Integration.Map_PolCoverageType MapCvg
         WITH (NOLOCK)
         ON C.COVERAGE_TYPE_ID = MapCvg.CovgTypeId and
             right (coverage.SrceSysIntRef, charindex('~', reverse(coverage.SrceSysIntRef)) - 1) = MapCvg.PolCovgType
             left join EDW_Integration.Integration.Ref_CurrencyCode refcurrcode
         WITH (NOLOCK)
         ON case
             when b.CAN_CAN_BUS = 'Y'
             then 'CAD'
             else 'USD'
             end = refcurrcode.CurCode
             left join EDW_Integration.Integration.Ref_RateOfExchange ROE
         WITH (NOLOCK)
         ON concat(year (b.record_date), case
             when cast (month (b.record_date) as varchar) in
             ('1','2','3','4','5','6','7','8','9')
             then concat('0', month (b.record_date))
             else cast (month (b.record_date) as varchar)
             end
             ) = ROE.AcctPrd and
             refcurrcode.CurCodeId = ROE.ForCurCodeId
             left join EDW_Integration.Integration.Map_CurrencyCode mapcurrcode
         WITH (NOLOCK)
         ON refcurrcode.CurCodeId = mapcurrcode.CurCodeId and
             mapcurrcode.SrceSysId = 21
        where 
     ) a;
