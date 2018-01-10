/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="estado_cheque", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class EstadoCheque
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="estado_cheque", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="estado_cheque")
/*  24:    */   @Column(name="id_estado_cheque")
/*  25:    */   private int idEstadoCheque;
/*  26:    */   @Column(name="id_organizacion")
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal")
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="codigo", nullable=false, length=20)
/*  31:    */   @NotNull
/*  32:    */   @Size(min=1, max=20)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=100)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=2, max=100)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="estado_inicial", nullable=false)
/*  39:    */   private boolean estadoInicial;
/*  40:    */   @Column(name="estado_final", nullable=false)
/*  41:    */   private boolean estadoFinal;
/*  42:    */   
/*  43:    */   public int getId()
/*  44:    */   {
/*  45: 70 */     return this.idEstadoCheque;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdEstadoCheque()
/*  49:    */   {
/*  50: 77 */     return this.idEstadoCheque;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdEstadoCheque(int idEstadoCheque)
/*  54:    */   {
/*  55: 85 */     this.idEstadoCheque = idEstadoCheque;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdOrganizacion()
/*  59:    */   {
/*  60: 92 */     return this.idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdOrganizacion(int idOrganizacion)
/*  64:    */   {
/*  65:100 */     this.idOrganizacion = idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdSucursal()
/*  69:    */   {
/*  70:107 */     return this.idSucursal;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdSucursal(int idSucursal)
/*  74:    */   {
/*  75:115 */     this.idSucursal = idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getCodigo()
/*  79:    */   {
/*  80:122 */     return this.codigo;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setCodigo(String codigo)
/*  84:    */   {
/*  85:130 */     this.codigo = codigo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getNombre()
/*  89:    */   {
/*  90:137 */     return this.nombre;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setNombre(String nombre)
/*  94:    */   {
/*  95:145 */     this.nombre = nombre;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isEstadoInicial()
/*  99:    */   {
/* 100:152 */     return this.estadoInicial;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setEstadoInicial(boolean estadoInicial)
/* 104:    */   {
/* 105:160 */     this.estadoInicial = estadoInicial;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isEstadoFinal()
/* 109:    */   {
/* 110:167 */     return this.estadoFinal;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setEstadoFinal(boolean estadoFinal)
/* 114:    */   {
/* 115:175 */     this.estadoFinal = estadoFinal;
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.EstadoCheque
 * JD-Core Version:    0.7.0.1
 */