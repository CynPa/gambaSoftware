/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Temporal;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ import javax.validation.constraints.DecimalMin;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="dotacion_empleado")
/*  24:    */ public class DotacionEmpleado
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -1133788217704360695L;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="dotacion_empleado", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="dotacion_empleado")
/*  36:    */   @Column(name="id_dotacion_empleado", unique=true, nullable=false)
/*  37:    */   private int idDotacionEmpleado;
/*  38:    */   @Column(name="fecha_entrega", nullable=false)
/*  39:    */   @Temporal(TemporalType.DATE)
/*  40:    */   private Date fechaEntrega;
/*  41:    */   @Column(name="fecha_reposicion", nullable=false)
/*  42:    */   @Temporal(TemporalType.DATE)
/*  43:    */   private Date fechaReposicion;
/*  44:    */   @Column(name="descripcion", nullable=false, length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="cantidad_dotada", nullable=false, precision=12, scale=2)
/*  48:    */   @DecimalMin("0.00")
/*  49: 57 */   private BigDecimal cantidadDotada = BigDecimal.ZERO;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_producto", nullable=false, insertable=true, updatable=true)
/*  52:    */   @NotNull
/*  53:    */   private Producto producto;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_empleado", nullable=false, insertable=true, updatable=false)
/*  56:    */   @NotNull
/*  57:    */   private Empleado empleado;
/*  58:    */   
/*  59:    */   public int getId()
/*  60:    */   {
/*  61: 77 */     return this.idDotacionEmpleado;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdOrganizacion()
/*  65:    */   {
/*  66: 81 */     return this.idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71: 85 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdSucursal()
/*  75:    */   {
/*  76: 89 */     return this.idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdSucursal(int idSucursal)
/*  80:    */   {
/*  81: 93 */     this.idSucursal = idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdDotacionEmpleado()
/*  85:    */   {
/*  86: 98 */     return this.idDotacionEmpleado;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdDotacionEmpleado(int idDotacionEmpleado)
/*  90:    */   {
/*  91:102 */     this.idDotacionEmpleado = idDotacionEmpleado;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getDescripcion()
/*  95:    */   {
/*  96:106 */     return this.descripcion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setDescripcion(String descripcion)
/* 100:    */   {
/* 101:110 */     this.descripcion = descripcion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Date getFechaEntrega()
/* 105:    */   {
/* 106:115 */     return this.fechaEntrega;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setFechaEntrega(Date fechaEntrega)
/* 110:    */   {
/* 111:119 */     this.fechaEntrega = fechaEntrega;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Date getFechaReposicion()
/* 115:    */   {
/* 116:123 */     return this.fechaReposicion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setFechaReposicion(Date fechaReposicion)
/* 120:    */   {
/* 121:127 */     this.fechaReposicion = fechaReposicion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Producto getProducto()
/* 125:    */   {
/* 126:131 */     return this.producto;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setProducto(Producto producto)
/* 130:    */   {
/* 131:135 */     this.producto = producto;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Empleado getEmpleado()
/* 135:    */   {
/* 136:139 */     return this.empleado;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setEmpleado(Empleado empleado)
/* 140:    */   {
/* 141:143 */     this.empleado = empleado;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public BigDecimal getCantidadDotada()
/* 145:    */   {
/* 146:147 */     return this.cantidadDotada;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setCantidadDotada(BigDecimal cantidadDotada)
/* 150:    */   {
/* 151:151 */     this.cantidadDotada = cantidadDotada;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DotacionEmpleado
 * JD-Core Version:    0.7.0.1
 */