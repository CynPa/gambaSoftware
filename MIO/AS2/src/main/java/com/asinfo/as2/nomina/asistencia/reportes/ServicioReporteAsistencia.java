package com.asinfo.as2.nomina.asistencia.reportes;

import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.Empleado;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteAsistencia
{
  public abstract List<Object[]> getReporteAsistencia(Empleado paramEmpleado, Departamento paramDepartamento, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract List<Object[]> getReporteHorarioPersonalizado(Departamento paramDepartamento, Date paramDate1, Date paramDate2);
  
  public abstract List<Object[]> getReporteAsistenciaResumido(Empleado paramEmpleado, Departamento paramDepartamento, Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract List<Object[]> getReporteControlAsistenciSobretiempos(Departamento paramDepartamento, Date paramDate, Empleado paramEmpleado);
  
  public abstract List<Object[]> getReporteAsistenciaSemanal(Empleado paramEmpleado, Departamento paramDepartamento, Date paramDate1, Date paramDate2, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.reportes.ServicioReporteAsistencia
 * JD-Core Version:    0.7.0.1
 */