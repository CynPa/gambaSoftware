/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.OneToMany;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="variable", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"}), @javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  22:    */ public class Variable
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="variable", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="variable")
/*  30:    */   @Column(name="id_variable")
/*  31:    */   private int idVariable;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private int idOrganizacion;
/*  38:    */   @Column(name="codigo", nullable=false, length=10)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=10)
/*  41:    */   private String codigo;
/*  42:    */   @Column(name="nombre", nullable=false, length=50)
/*  43:    */   @Size(max=50)
/*  44:    */   @NotNull
/*  45:    */   private String nombre;
/*  46:    */   @Column(name="descripcion", nullable=true, length=200)
/*  47:    */   @Size(max=200)
/*  48:    */   private String descripcion;
/*  49:    */   @Column(name="estado", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private boolean estado;
/*  52:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="variable")
/*  53: 80 */   private List<DetalleVariable> listaDetalleVariable = new ArrayList();
/*  54:    */   @Transient
/*  55: 83 */   private BigDecimal valor = BigDecimal.ZERO;
/*  56:    */   
/*  57:    */   public int getId()
/*  58:    */   {
/*  59: 97 */     return getIdVariable();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdVariable()
/*  63:    */   {
/*  64:101 */     return this.idVariable;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdVariable(int idVariable)
/*  68:    */   {
/*  69:105 */     this.idVariable = idVariable;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdSucursal()
/*  73:    */   {
/*  74:109 */     return this.idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdSucursal(int idSucursal)
/*  78:    */   {
/*  79:113 */     this.idSucursal = idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84:117 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:121 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getCodigo()
/*  93:    */   {
/*  94:125 */     return this.codigo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setCodigo(String codigo)
/*  98:    */   {
/*  99:129 */     this.codigo = codigo;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getNombre()
/* 103:    */   {
/* 104:133 */     return this.nombre;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setNombre(String nombre)
/* 108:    */   {
/* 109:137 */     this.nombre = nombre;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getDescripcion()
/* 113:    */   {
/* 114:141 */     return this.descripcion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDescripcion(String descripcion)
/* 118:    */   {
/* 119:145 */     this.descripcion = descripcion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isEstado()
/* 123:    */   {
/* 124:149 */     return this.estado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setEstado(boolean estado)
/* 128:    */   {
/* 129:153 */     this.estado = estado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<DetalleVariable> getListaDetalleVariable()
/* 133:    */   {
/* 134:158 */     return this.listaDetalleVariable;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setListaDetalleVariable(List<DetalleVariable> listaDetalleVariable)
/* 138:    */   {
/* 139:163 */     this.listaDetalleVariable = listaDetalleVariable;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public BigDecimal getValor()
/* 143:    */   {
/* 144:168 */     return this.valor;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setValor(BigDecimal valor)
/* 148:    */   {
/* 149:173 */     this.valor = valor;
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Variable
 * JD-Core Version:    0.7.0.1
 */