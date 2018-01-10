/*   1:    */ package com.asinfo.as2.entities.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DimensionContable;
/*   4:    */ import com.asinfo.as2.entities.Ejercicio;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.entities.Periodo;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.OneToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="presupuesto")
/*  26:    */ public class Presupuesto
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="presupuesto", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="presupuesto")
/*  33:    */   @Column(name="id_presupuesto")
/*  34:    */   private int idPresupuesto;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  37:    */   private Sucursal sucursal;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idOrganizacion;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_ejercicio", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private Ejercicio ejercicio;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_periodo", nullable=true)
/*  47:    */   private Periodo periodo;
/*  48:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="presupuesto")
/*  49: 80 */   private List<DetallePresupuesto> listaDetallePresupuesto = new ArrayList();
/*  50:    */   @OneToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_dimension_contable", nullable=true)
/*  52:    */   private DimensionContable dimensionContable;
/*  53:    */   
/*  54:    */   public int getIdPresupuesto()
/*  55:    */   {
/*  56:105 */     return this.idPresupuesto;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdPresupuesto(int idPresupuesto)
/*  60:    */   {
/*  61:115 */     this.idPresupuesto = idPresupuesto;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Periodo getPeriodo()
/*  65:    */   {
/*  66:124 */     return this.periodo;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setPeriodo(Periodo periodo)
/*  70:    */   {
/*  71:134 */     this.periodo = periodo;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<DetallePresupuesto> getListaDetallePresupuesto()
/*  75:    */   {
/*  76:143 */     return this.listaDetallePresupuesto;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setListaDetallePresupuesto(List<DetallePresupuesto> listaDetallePresupuesto)
/*  80:    */   {
/*  81:153 */     this.listaDetallePresupuesto = listaDetallePresupuesto;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Sucursal getSucursal()
/*  85:    */   {
/*  86:162 */     return this.sucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setSucursal(Sucursal sucursal)
/*  90:    */   {
/*  91:172 */     this.sucursal = sucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdOrganizacion()
/*  95:    */   {
/*  96:181 */     return this.idOrganizacion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdOrganizacion(int idOrganizacion)
/* 100:    */   {
/* 101:191 */     this.idOrganizacion = idOrganizacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Ejercicio getEjercicio()
/* 105:    */   {
/* 106:200 */     return this.ejercicio;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setEjercicio(Ejercicio ejercicio)
/* 110:    */   {
/* 111:210 */     this.ejercicio = ejercicio;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getId()
/* 115:    */   {
/* 116:220 */     return this.idPresupuesto;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public DimensionContable getDimensionContable()
/* 120:    */   {
/* 121:224 */     return this.dimensionContable;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setDimensionContable(DimensionContable dimensionContable)
/* 125:    */   {
/* 126:228 */     this.dimensionContable = dimensionContable;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.presupuesto.Presupuesto
 * JD-Core Version:    0.7.0.1
 */