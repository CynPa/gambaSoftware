package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.EstadoCheque;
import com.asinfo.as2.entities.MovimientoBancario;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMovimientoBancario
{
  public abstract void guardar(MovimientoBancario paramMovimientoBancario)
    throws ExcepcionAS2Financiero, ExcepcionAS2, Exception;
  
  public abstract void actualizar(MovimientoBancario paramMovimientoBancario);
  
  public abstract void eliminar(MovimientoBancario paramMovimientoBancario);
  
  public abstract MovimientoBancario buscarPorId(Integer paramInteger);
  
  public abstract List<MovimientoBancario> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract MovimientoBancario recuperaDetalleBancario(int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<MovimientoBancario> getListaMovimientoBancarioPorAsiento(int paramInt);
  
  public abstract void anular(MovimientoBancario paramMovimientoBancario)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void esEditable(MovimientoBancario paramMovimientoBancario)
    throws ExcepcionAS2Financiero;
  
  public abstract void cambiarEstado(MovimientoBancario paramMovimientoBancario, EstadoCheque paramEstadoCheque, String paramString)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario
 * JD-Core Version:    0.7.0.1
 */