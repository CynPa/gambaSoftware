package com.asinfo.as2.financiero.pagos.configuracion.servicio;

import com.asinfo.as2.entities.EstadoCheque;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEstadoCheque
{
  public abstract void guardar(EstadoCheque paramEstadoCheque)
    throws ExcepcionAS2;
  
  public abstract void eliminar(EstadoCheque paramEstadoCheque);
  
  public abstract EstadoCheque buscarPorId(int paramInt);
  
  public abstract List<EstadoCheque> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<EstadoCheque> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract EstadoCheque cargarDetalle(int paramInt);
  
  public abstract EstadoCheque getEstadoFinal(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.configuracion.servicio.ServicioEstadoCheque
 * JD-Core Version:    0.7.0.1
 */