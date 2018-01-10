/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_lista_verificacion")
/*  22:    */ public class DetalleListaVerificacion
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -1274077298940427668L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_lista_verificacion", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_lista_verificacion")
/*  29:    */   @Column(name="id_detalle_lista_verificacion")
/*  30:    */   private int idDetalleListaVerificacion;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="pregunta", nullable=false, length=200)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=200)
/*  38:    */   private String pregunta;
/*  39:    */   @Column(name="tipo_atributo", nullable=false)
/*  40:    */   @Enumerated(EnumType.ORDINAL)
/*  41:    */   private TipoAtributo tipoAtributo;
/*  42:    */   @Column(name="valores", nullable=false, length=200)
/*  43:    */   @NotNull
/*  44:    */   @Size(max=200)
/*  45:    */   private String valores;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_lista_verificacion", nullable=true)
/*  48:    */   private ListaVerificacion listaVerificacion;
/*  49:    */   
/*  50:    */   public int getIdDetalleListaVerificacion()
/*  51:    */   {
/*  52: 86 */     return this.idDetalleListaVerificacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdDetalleListaVerificacion(int idDetalleListaVerificacion)
/*  56:    */   {
/*  57: 96 */     this.idDetalleListaVerificacion = idDetalleListaVerificacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdOrganizacion()
/*  61:    */   {
/*  62:105 */     return this.idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdOrganizacion(int idOrganizacion)
/*  66:    */   {
/*  67:115 */     this.idOrganizacion = idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdSucursal()
/*  71:    */   {
/*  72:124 */     return this.idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdSucursal(int idSucursal)
/*  76:    */   {
/*  77:134 */     this.idSucursal = idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getPregunta()
/*  81:    */   {
/*  82:143 */     return this.pregunta;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setPregunta(String pregunta)
/*  86:    */   {
/*  87:153 */     this.pregunta = pregunta;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public TipoAtributo getTipoAtributo()
/*  91:    */   {
/*  92:162 */     return this.tipoAtributo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setTipoAtributo(TipoAtributo tipoAtributo)
/*  96:    */   {
/*  97:172 */     this.tipoAtributo = tipoAtributo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getValores()
/* 101:    */   {
/* 102:181 */     return this.valores;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setValores(String valores)
/* 106:    */   {
/* 107:191 */     this.valores = valores;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public ListaVerificacion getListaVerificacion()
/* 111:    */   {
/* 112:200 */     return this.listaVerificacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setListaVerificacion(ListaVerificacion listaVerificacion)
/* 116:    */   {
/* 117:210 */     this.listaVerificacion = listaVerificacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getId()
/* 121:    */   {
/* 122:215 */     return this.idDetalleListaVerificacion;
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.DetalleListaVerificacion
 * JD-Core Version:    0.7.0.1
 */