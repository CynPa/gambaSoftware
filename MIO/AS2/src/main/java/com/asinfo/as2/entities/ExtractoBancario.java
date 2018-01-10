/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.OneToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Temporal;
/*  20:    */ import javax.persistence.TemporalType;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="extracto_bancario")
/*  26:    */ public class ExtractoBancario
/*  27:    */   extends EntidadBase
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="extracto_bancario", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="extracto_bancario")
/*  34:    */   @Column(name="id_extracto_bancario")
/*  35:    */   private int idExtractoBancario;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   private int idOrganizacion;
/*  38:    */   @Column(name="id_sucursal", nullable=false)
/*  39:    */   private int idSucursal;
/*  40:    */   @NotNull
/*  41:    */   @Temporal(TemporalType.DATE)
/*  42:    */   @Column(name="fecha", nullable=false)
/*  43:    */   private Date fecha;
/*  44:    */   @OneToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  46:    */   private Asiento asiento;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_interfaz_contable_proceso", nullable=true)
/*  49:    */   private InterfazContableProceso interfazContableProceso;
/*  50:    */   @Column(name="descripcion", length=200)
/*  51:    */   @Size(max=200)
/*  52:    */   private String descripcion;
/*  53:    */   @OneToMany(mappedBy="extractoBancario", fetch=FetchType.LAZY)
/*  54: 78 */   List<DetalleExtractoBancario> listaDetalleExtractoBancario = new ArrayList();
/*  55:    */   
/*  56:    */   public int getId()
/*  57:    */   {
/*  58: 86 */     return this.idExtractoBancario;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdExtractoBancario()
/*  62:    */   {
/*  63: 90 */     return this.idExtractoBancario;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdExtractoBancario(int idExtractoBancario)
/*  67:    */   {
/*  68: 94 */     this.idExtractoBancario = idExtractoBancario;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdOrganizacion()
/*  72:    */   {
/*  73: 98 */     return this.idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdOrganizacion(int idOrganizacion)
/*  77:    */   {
/*  78:102 */     this.idOrganizacion = idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdSucursal()
/*  82:    */   {
/*  83:106 */     return this.idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdSucursal(int idSucursal)
/*  87:    */   {
/*  88:110 */     this.idSucursal = idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public InterfazContableProceso getInterfazContableProceso()
/*  92:    */   {
/*  93:114 */     return this.interfazContableProceso;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/*  97:    */   {
/*  98:118 */     this.interfazContableProceso = interfazContableProceso;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getDescripcion()
/* 102:    */   {
/* 103:122 */     return this.descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setDescripcion(String descripcion)
/* 107:    */   {
/* 108:126 */     this.descripcion = descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<DetalleExtractoBancario> getListaDetalleExtractoBancario()
/* 112:    */   {
/* 113:130 */     return this.listaDetalleExtractoBancario;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setListaDetalleExtractoBancario(List<DetalleExtractoBancario> listaDetalleExtractoBancario)
/* 117:    */   {
/* 118:134 */     this.listaDetalleExtractoBancario = listaDetalleExtractoBancario;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Date getFecha()
/* 122:    */   {
/* 123:138 */     return this.fecha;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setFecha(Date fecha)
/* 127:    */   {
/* 128:142 */     this.fecha = fecha;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Asiento getAsiento()
/* 132:    */   {
/* 133:146 */     return this.asiento;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setAsiento(Asiento asiento)
/* 137:    */   {
/* 138:150 */     this.asiento = asiento;
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ExtractoBancario
 * JD-Core Version:    0.7.0.1
 */