/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   8:    */ import com.asinfo.as2.entities.PrefacturaCliente;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  12:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPrefacturaCliente;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.event.SelectEvent;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class LiberarPrefacturaClienteBean
/*  26:    */   extends PageController
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private transient ServicioPrefacturaCliente servicioPrefacturaCliente;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioEmpresa servicioEmpresa;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  35:    */   private FacturaCliente facturaCliente;
/*  36:    */   private Empresa empresa;
/*  37: 58 */   List<PrefacturaCliente> listaPrefacturaCliente = new ArrayList();
/*  38:    */   
/*  39:    */   public void cargarPrefacturas(SelectEvent event)
/*  40:    */   {
/*  41: 61 */     this.facturaCliente = ((FacturaCliente)event.getObject());
/*  42: 62 */     if (this.facturaCliente != null) {
/*  43: 63 */       this.listaPrefacturaCliente = this.servicioPrefacturaCliente.getListaPrefacturaCliente(this.facturaCliente);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void liberarPrefacturas()
/*  48:    */   {
/*  49: 68 */     if (this.facturaCliente != null) {
/*  50:    */       try
/*  51:    */       {
/*  52: 70 */         this.servicioPrefacturaCliente.liberarPrefacturaCliente(this.facturaCliente);
/*  53:    */         
/*  54: 72 */         this.facturaCliente = null;
/*  55: 73 */         this.listaPrefacturaCliente = new ArrayList();
/*  56:    */         
/*  57: 75 */         addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/*  58:    */       }
/*  59:    */       catch (ExcepcionAS2 e)
/*  60:    */       {
/*  61: 78 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  62: 79 */         LOG.info("ERROR AL LIBERAR PREFACTURA CLIENTE", e);
/*  63:    */       }
/*  64:    */       catch (Exception e)
/*  65:    */       {
/*  66: 81 */         e.printStackTrace();
/*  67: 82 */         addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  68: 83 */         LOG.info("ERROR AL LIBERAR PREFACTURA CLIENTE", e);
/*  69:    */       }
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<Empresa> autocompletarClientes(String consulta)
/*  74:    */   {
/*  75: 89 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List<FacturaCliente> autocompletarFacturas(String consulta)
/*  79:    */   {
/*  80: 99 */     Map<String, String> filters = new HashMap();
/*  81:100 */     List<FacturaCliente> lista = new ArrayList();
/*  82:102 */     if (this.empresa != null)
/*  83:    */     {
/*  84:103 */       filters.put("empresa.idEmpresa", "" + getEmpresa().getId());
/*  85:104 */       filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/*  86:106 */       if ((consulta != null) && (!consulta.isEmpty())) {
/*  87:107 */         filters.put("numero", "%" + consulta);
/*  88:    */       }
/*  89:110 */       lista = this.servicioFacturaCliente.obtenerListaCombo("fecha", true, filters);
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:112 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  94:    */     }
/*  95:115 */     return lista;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public FacturaCliente getFacturaCliente()
/*  99:    */   {
/* 100:125 */     return this.facturaCliente;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 104:    */   {
/* 105:135 */     this.facturaCliente = facturaCliente;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public List<PrefacturaCliente> getListaPrefacturaCliente()
/* 109:    */   {
/* 110:144 */     return this.listaPrefacturaCliente;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setListaPrefacturaCliente(List<PrefacturaCliente> listaPrefacturaCliente)
/* 114:    */   {
/* 115:154 */     this.listaPrefacturaCliente = listaPrefacturaCliente;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Empresa getEmpresa()
/* 119:    */   {
/* 120:163 */     return this.empresa;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setEmpresa(Empresa empresa)
/* 124:    */   {
/* 125:173 */     this.empresa = empresa;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.LiberarPrefacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */