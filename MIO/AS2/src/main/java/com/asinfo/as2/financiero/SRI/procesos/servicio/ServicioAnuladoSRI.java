package com.asinfo.as2.financiero.SRI.procesos.servicio;

import com.asinfo.as2.entities.sri.AnuladoSRI;
import com.asinfo.as2.entities.sri.FacturaClienteSRI;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAnuladoSRI
{
  public abstract void guardar(AnuladoSRI paramAnuladoSRI)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(AnuladoSRI paramAnuladoSRI);
  
  public abstract AnuladoSRI buscarPorId(int paramInt);
  
  public abstract List<AnuladoSRI> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void anularFacturaCliente(FacturaClienteSRI paramFacturaClienteSRI);
  
  public abstract List<AnuladoSRI> obtenerAnuladosMes(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List<AnuladoSRI> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAnuladoSRI
 * JD-Core Version:    0.7.0.1
 */