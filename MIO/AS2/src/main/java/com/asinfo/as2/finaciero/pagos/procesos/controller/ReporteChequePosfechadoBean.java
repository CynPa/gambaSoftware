/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ReporteChequePosfechadoBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -8338346628015918176L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioMovimientoBancario servicioMovimientoBancario;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioEmpresa servicioEmpresa;
/*  35:    */   @EJB
/*  36:    */   private transient ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  37:    */   private Date fechaDesdePosfechado;
/*  38:    */   private Date fechaHastaPosfechado;
/*  39:    */   private Empresa empresaSeleccionada;
/*  40:    */   private LazyDataModel<MovimientoBancario> listaChequesPosfechadosPagos;
/*  41:    */   private DataTable dtChequesPosfechados;
/*  42:    */   
/*  43:    */   @PostConstruct
/*  44:    */   public void init()
/*  45:    */   {
/*  46: 65 */     this.fechaDesdePosfechado = FuncionesUtiles.setAtributoFecha(new Date());
/*  47: 66 */     this.fechaHastaPosfechado = this.fechaDesdePosfechado;
/*  48:    */     
/*  49: 68 */     this.listaChequesPosfechadosPagos = new LazyDataModel()
/*  50:    */     {
/*  51:    */       private static final long serialVersionUID = 609994324204466060L;
/*  52:    */       
/*  53:    */       public List<MovimientoBancario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  54:    */       {
/*  55: 80 */         List<MovimientoBancario> lista = new ArrayList();
/*  56: 81 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  57:    */         
/*  58: 83 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  59: 84 */         filters.put("estado", "!=" + Estado.ANULADO.toString());
/*  60: 85 */         filters.put("fechaPosfechadoDesde", FuncionesUtiles.dateToString(ReporteChequePosfechadoBean.this.fechaDesdePosfechado));
/*  61: 86 */         filters.put("fechaPosfechadoHasta", FuncionesUtiles.dateToString(ReporteChequePosfechadoBean.this.fechaHastaPosfechado));
/*  62: 87 */         lista = ReporteChequePosfechadoBean.this.servicioMovimientoBancario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  63: 88 */         ReporteChequePosfechadoBean.this.listaChequesPosfechadosPagos.setRowCount(ReporteChequePosfechadoBean.this.servicioMovimientoBancario.contarPorCriterio(filters));
/*  64: 89 */         return lista;
/*  65:    */       }
/*  66:    */     };
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String cargarDatos()
/*  70:    */   {
/*  71:101 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String limpiar()
/*  75:    */   {
/*  76:110 */     return "";
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Date getFechaDesdePosfechado()
/*  80:    */   {
/*  81:117 */     return this.fechaDesdePosfechado;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setFechaDesdePosfechado(Date fechaDesdePosfechado)
/*  85:    */   {
/*  86:125 */     this.fechaDesdePosfechado = fechaDesdePosfechado;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Date getFechaHastaPosfechado()
/*  90:    */   {
/*  91:132 */     return this.fechaHastaPosfechado;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setFechaHastaPosfechado(Date fechaHastaPosfechado)
/*  95:    */   {
/*  96:140 */     this.fechaHastaPosfechado = fechaHastaPosfechado;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public LazyDataModel<MovimientoBancario> getListaChequesPosfechadosPagos()
/* 100:    */   {
/* 101:147 */     return this.listaChequesPosfechadosPagos;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setListaChequesPosfechadosPagos(LazyDataModel<MovimientoBancario> listaChequesPosfechadosPagos)
/* 105:    */   {
/* 106:155 */     this.listaChequesPosfechadosPagos = listaChequesPosfechadosPagos;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public DataTable getDtChequesPosfechados()
/* 110:    */   {
/* 111:162 */     return this.dtChequesPosfechados;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setDtChequesPosfechados(DataTable dtChequesPosfechados)
/* 115:    */   {
/* 116:170 */     this.dtChequesPosfechados = dtChequesPosfechados;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Empresa getEmpresaSeleccionada()
/* 120:    */   {
/* 121:177 */     return this.empresaSeleccionada;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setEmpresaSeleccionada(Empresa empresaSeleccionada)
/* 125:    */   {
/* 126:185 */     this.empresaSeleccionada = empresaSeleccionada;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String editar()
/* 130:    */   {
/* 131:190 */     return null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String guardar()
/* 135:    */   {
/* 136:195 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String eliminar()
/* 140:    */   {
/* 141:200 */     return null;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.ReporteChequePosfechadoBean
 * JD-Core Version:    0.7.0.1
 */