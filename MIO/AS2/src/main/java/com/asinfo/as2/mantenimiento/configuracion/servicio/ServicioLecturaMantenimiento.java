package com.asinfo.as2.mantenimiento.configuracion.servicio;

import com.asinfo.as2.entities.mantenimiento.Equipo;
import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioLecturaMantenimiento
{
  public abstract void guardar(LecturaMantenimiento paramLecturaMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void eliminar(LecturaMantenimiento paramLecturaMantenimiento)
    throws AS2ExceptionMantenimiento, ExcepcionAS2;
  
  public abstract LecturaMantenimiento buscarPorId(Integer paramInteger);
  
  public abstract List<LecturaMantenimiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract LecturaMantenimiento cargarDetalle(LecturaMantenimiento paramLecturaMantenimiento);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<LecturaMantenimiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void guardar(LecturaMantenimiento paramLecturaMantenimiento, List<LecturaMantenimiento> paramList)
    throws AS2ExceptionMantenimiento, ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarValoresLecturaMantenimiento(LecturaMantenimiento paramLecturaMantenimiento, boolean paramBoolean);
  
  public abstract List<LecturaMantenimiento> obtenerLecturasEquipo(Equipo paramEquipo);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioLecturaMantenimiento
 * JD-Core Version:    0.7.0.1
 */